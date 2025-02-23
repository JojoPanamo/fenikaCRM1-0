package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.*;
import com.example.fenikaCRM10.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealService {
    private final DealRepository dealRepository;
    private final StatusesRepository statusesRepository;
    private final PaymentsService paymentsService;
    private final UserService userService;
    private final PaymentsRepository paymentsRepository;
    private final CommentRepository commentRepository;
    private final StatusesService statusesService;
    private final DealServiceList dealServiceList;
    private final WeeklyManagerProfitRepository weeklyManagerProfitRepository;
//    private final ManagerProfitService managerProfitService;


    // Метод для поиска сделок по имени
    public List<Deal> listDeals(String name) {
        if (name != null) {
            return dealRepository.findDealByName(name);  // Поиск сделок по имени
        } else {
            return dealRepository.findAll();  // Возвращаем все сделки
        }
    }

    // Метод для сохранения сделки
    public void saveDeal(Deal deal) {
        if (deal.getCreationDate() == null) {
            deal.setCreationDate(LocalDate.now());
        }
        dealRepository.save(deal);  // Сохранение сделки в базу данных
        Statuses newStatus = new Statuses();
        newStatus.setDealId(deal.getDealId());  // Присваиваем ID сделки
        newStatus.setStatusChoose("Новая заявка");  // Устанавливаем статус "Новая заявка"
        newStatus.setStatusComment("связаться в течение 30 минут!");    // Устанавливаем комментарий
        newStatus.setCurrentDate(DateService.getCurrentDate());
        deal.setLastStatus(deal.getLastStatus());// Устанавливаем текущую дату

        // Сохраняем новый статус в базу данных
        statusesRepository.save(newStatus);

        log.info("Сделке с ID {} присвоен статус 'Новая заявка' с комментарием 'связаться в течение 30 минут!'", deal.getDealId());
    }

    // Метод для получения сделки по ID
    public Deal getDealById(Long dealId) {
        return dealRepository.findById(dealId).orElse(null);  // Поиск сделки по ID
    }
    public List<Deal> findByUser(User user) {
        List<Deal> userDeals = dealRepository.findByUser(user);
        for (Deal deal : userDeals) {
            // Получаем последний статус сделки
            Statuses lastStatus = statusesRepository.findLastStatusByDealId(deal.getDealId());

            if (lastStatus != null) {
                deal.setLastStatus(lastStatus.getStatusChoose());  // Устанавливаем последний статус
            }
        }
        return userDeals;
    }
    public List<Deal> findByUser1(User user) {
        List<Deal> userDeals = dealRepository.findByUser(user);
        for (Deal deal : userDeals) {
            // Получаем последний статус сделки
            Comments lastComment = commentRepository.findLastCommentByDealId(deal.getDealId());
            if (lastComment != null) {
                deal.setLastStatus(lastComment.getComment());  // Устанавливаем последний статус
            }
        }
        return userDeals;
    }
    // Метод для одного статуса
    public int countDealsByLastStatusAndUser(User user, String status) {
        LocalDate now = LocalDate.now();
        return dealRepository.countDealsByLastStatusAndUserForMonth(user, status, now.getMonthValue(), now.getYear());
    }

    public void updateThinkSum(Long dealId, Double thinkSum) {
        // Ищем сделку по ID
        Deal deal = dealRepository.findById(dealId).orElseThrow(() -> new RuntimeException("Сделка не найдена"));

        // Обновляем сумму
        deal.setThinkSum(thinkSum);

        // Сохраняем изменения
        dealRepository.save(deal);
    }

    // Метод для списка статусов
    public int countDealsByStatusesAndUser(User user, List<String> statuses) {
        // Логируем входные параметры
        log.info("Входные параметры: пользователь {} и статусы {}", user.getEmail(), statuses);

        // Вызов репозитория
        int count = dealRepository.countDealsByLastStatusAndUser(user, statuses);

        // Логируем результат
        log.info("Количество сделок для пользователя {} и статусов {}: {}", user.getEmail(), statuses, count);

        return count;
    }
    public List<Deal> findDealsByStatuses(User user, List<String> statuses) {
        List<Deal> deals = dealRepository.findByUserAndStatuses(user, statuses);
        for (Deal deal : deals) {
            String lastStatus = statusesService.getLastStatusForDeal(deal.getDealId());
            String lastStatusDate = statusesService.getLastStatusDateForDeal(deal.getDealId());

            deal.setLastStatus(lastStatus != null ? lastStatus : "Статус не установлен");
            deal.setLastStatusDate(lastStatusDate != null ? lastStatusDate : "Дата не установлена");
        }
        return deals;  // Поиск сделок по статусам и пользователю
    }
    public List<Deal> getDealsForCurrentMonth() {
        LocalDate now = LocalDate.now();
        // Здесь нужно добавить метод в репозиторий, который фильтрует сделки по месяцу и году
        return dealRepository.findByMonthAndYear(now.getMonthValue(), now.getYear());
    }

    // Получение сделок для статистики с учетом фильтров
    public StatisticsDTO getDealsForStatistics() {
        LocalDate now = LocalDate.now();
        List<Deal> allDeals = getDealsForCurrentMonth();

        // Фильтрация завершенных и отказанных сделок
        List<Deal> completedDeals = allDeals.stream()
                .filter(deal -> "Завершен".equals(deal.getStatus()) || "Отказ".equals(deal.getStatus()))
                .collect(Collectors.toList());

        // Фильтрация сделок для переноса в следующий месяц
        List<Deal> pendingDeals = allDeals.stream()
                .filter(deal -> "В работе".equals(deal.getStatus()) ||
                        "Оплачен".equals(deal.getStatus()) ||
                        "Новая заявка".equals(deal.getStatus()))
                .collect(Collectors.toList());

        // Расчет прибыли компании по завершенным сделкам
        double companyProfit = completedDeals.stream()
                .mapToDouble(deal -> paymentsService.getCompanyProfit(deal.getDealId()))
                .sum();

        // Расчет прибыли менеджера
        User currentUser = userService.findByPrincipal(
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        double managerProfit = completedDeals.stream()
                .mapToDouble(deal -> paymentsService.getManagerProfit(deal.getDealId(), currentUser))
                .sum();

        // Сумма поступлений за текущий месяц
        double totalPayments = allDeals.stream()
                .mapToDouble(deal -> paymentsService.getTotalPayments(deal.getDealId()))
                .sum();

        // Преобразуем списки сделок в списки названий для конструктора StatisticsDTO
        List<String> completedDealNames = completedDeals.stream()
                .map(Deal::getName)
                .collect(Collectors.toList());

        List<String> pendingDealNames = pendingDeals.stream()
                .map(Deal::getName)
                .collect(Collectors.toList());

        return new StatisticsDTO(companyProfit, managerProfit, totalPayments, completedDealNames, pendingDealNames);
    }
    public List<Deal> findDealsByStatusesForAdmin(List<String> statuses) {

        List<Deal> deals = dealRepository.findAllDealsByStatuses(statuses);
        for (Deal deal : deals) {
            String lastStatus = statusesService.getLastStatusForDeal(deal.getDealId());
            String lastStatusDate = statusesService.getLastStatusDateForDeal(deal.getDealId());

            deal.setLastStatus(lastStatus != null ? lastStatus : "Статус не установлен");
            deal.setLastStatusDate(lastStatusDate != null ? lastStatusDate : "Дата не установлена");
        }
        return deals;
    }
    public int getTotalCompletedDealsCount() {
        LocalDate now = LocalDate.now();
        return dealRepository.countDealsByLastStatus("Завершен", now.getMonthValue(), now.getYear());
    }

    public int getTotalRefusedDealsCount() {
        LocalDate now = LocalDate.now();
        return dealRepository.countDealsByLastStatus("Отказ", now.getMonthValue(), now.getYear());
    }

    public int getTotalInProgressOrPaidDealsCount() {
        List<String> statuses = Arrays.asList("В работе", "Оплачен", "Новая заявка");
        return dealRepository.countDealsByLastStatuses(statuses);
    }
    public Map<String, Integer> getDealsCountBySource() {
        // Получаем все возможные источники из DealServiceList
        List<String> sources = DealServiceList.getAuthors();
        Map<String, Integer> dealsCountBySource = new HashMap<>();

        // Подсчитываем количество сделок для каждого источника
        for (String source : sources) {
            int count = dealRepository.countByWhereFrom(source);
            dealsCountBySource.put(source, count);
        }

        return dealsCountBySource;
    }
    public Map<String, Integer> getDealsCountBySourceForCurrentMonth() {
        List<String> sources = DealServiceList.getAuthors();
        Map<String, Integer> dealsCountBySource = new HashMap<>();

        // Получаем текущий месяц и год
        YearMonth currentMonth = YearMonth.now();

        // Подсчитываем количество сделок для каждого источника, созданных в текущем месяце
        for (String source : sources) {
            int count = dealRepository.countByWhereFromAndMonthAndYear(source, currentMonth.getMonthValue(), currentMonth.getYear());
            dealsCountBySource.put(source, count);
        }

        return dealsCountBySource;
    }
    public List<Deal> getAllDealsWithTotalPayments() {
        List<Deal> deals = dealRepository.findAll();

        for (Deal deal : deals) {
            // Расчет суммы поступлений для каждой сделки
            double totalPayments = paymentsRepository.findByDealId(deal.getDealId())
                    .stream()
                    .mapToDouble(Payments::getSum)
                    .sum();
            deal.setTotalPayments(String.valueOf(totalPayments));
        }
        return deals;
    }
    public List<Deal> findDealsByUserId(Long userId) {
        if (userId == null) {
            return dealRepository.findAll();
        }
        return dealRepository.findByUser_UserId(userId);
    }
    public List<Deal> getAllDealsWithTotalPaymentsInner() {
        List<Deal> deals = dealRepository.findAll();
        for (Deal deal : deals) {
            double totalPayments = paymentsService.getTotalPaymentsInner(deal.getDealId());
            deal.setTotalPayments(String.valueOf(totalPayments));
        }
        return deals;
    }
    public Map<Integer, Double> getManagerProfitByWeeks(int year, int month, Long userId, boolean isAdmin) {
        List<Deal> deals = isAdmin
                ? dealRepository.findByMonthAndYear(month, year)
                : dealRepository.findByUser_UserId(userId).stream()
                .filter(deal -> deal.getCreationDate().getMonthValue() == month && deal.getCreationDate().getYear() == year)
                .collect(Collectors.toList());

        Map<Integer, Double> weeklyProfit = new HashMap<>();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        for (Deal deal : deals) {
            Optional<Statuses> completedStatus = statusesRepository.findTopByDealIdOrderByStatusIdDesc(deal.getDealId());
            if (completedStatus.isPresent() && "Завершен".equals(completedStatus.get().getStatusChoose())) {
                LocalDate statusDate = LocalDate.parse(completedStatus.get().getCurrentDate());
                int weekOfMonth = statusDate.get(weekFields.weekOfMonth());

                double profit = paymentsService.getManagerProfit(deal.getDealId(), userService.findById(userId));
                weeklyProfit.put(weekOfMonth, weeklyProfit.getOrDefault(weekOfMonth, 0.0) + profit);
            }
        }

        return weeklyProfit;
    }

//    public void markDealAsCompleted(Long dealId) {
//        Deal deal = dealRepository.findById(dealId).orElseThrow(() -> new RuntimeException("Сделка не найдена"));
//        Statuses completedStatus = new Statuses();
//        completedStatus.setDealId(deal.getDealId());
//        completedStatus.setStatusChoose("Завершен");
//        completedStatus.setCurrentDate(DateService.getCurrentDate());
//        statusesRepository.save(completedStatus);
//
//        // Добавляем запись в ManagerProfit
//        managerProfitService.addProfitRecord(deal);
//    }
public void updateLastStatus(Long dealId) {
    // Получаем последний статус из таблицы статусов
    Statuses lastStatus = statusesRepository.findLastStatusByDealId(dealId);
    if (lastStatus != null) {
        // Находим сделку
        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new RuntimeException("Сделка не найдена"));

        // Обновляем поле lastStatus
        deal.setLastStatus(lastStatus.getStatusChoose());

        // Сохраняем изменения
        dealRepository.save(deal);

        // Если статус завершен, обновляем WeeklyManagerProfit
        if ("Завершен".equals(lastStatus.getStatusChoose())) {
            LocalDate creationDate = deal.getCreationDate();
            User user = deal.getUser();
            int year = creationDate.getYear();
            int month = creationDate.getMonthValue();

            // Вызываем метод для обновления WeeklyManagerProfit
            updateWeeklyManagerProfit(user, year, month);
        }
    } else {
        log.warn("Не найден последний статус для сделки с ID: {}", dealId);
    }
}
    public void updateWeeklyManagerProfit(User user, int year, int month) {
        // Получаем все завершенные сделки пользователя за указанный месяц
        List<Deal> completedDeals = dealRepository.findByUserAndStatusAndMonthAndYear(
                user.getUserId(), "Завершен", month, year);

        // Проходим по всем неделям (максимум 5 недель в месяце)
        for (int week = 1; week <= 5; week++) {
            final int currentWeek = week;
            // Считаем прибыль менеджера за неделю
            double weeklyProfit = completedDeals.stream()
                    .filter(deal -> getWeekOfMonth(deal.getCreationDate()) == currentWeek)
                    .mapToDouble(Deal::getThinkSum)
                    .sum();

            // Обновляем или создаем запись в WeeklyManagerProfit
            WeeklyManagerProfit weeklyManagerProfit = weeklyManagerProfitRepository
                    .findByUserAndYearAndMonthAndWeek(user, year, month, week)
                    .orElse(new WeeklyManagerProfit(user, year, month, week, 0.0, 0.0));

            weeklyManagerProfit.setManagerProfit(weeklyProfit);
            weeklyManagerProfitRepository.save(weeklyManagerProfit);
            log.warn("Обновляем WeeklyManagerProfit для пользователя: {}, год: {}, месяц: {}", user.getName(), year, month);

        }
    }

    // Получение недели месяца
    private int getWeekOfMonth(LocalDate date) {
        return date.get(WeekFields.of(Locale.getDefault()).weekOfMonth());
    }




}
