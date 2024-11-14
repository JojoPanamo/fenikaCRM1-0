package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.*;
import com.example.fenikaCRM10.repositories.DealRepository;
import com.example.fenikaCRM10.repositories.PaymentsRepository;
import com.example.fenikaCRM10.repositories.StatusesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
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
        newStatus.setCurrentDate(DateService.getCurrentDate());     // Устанавливаем текущую дату

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
    // Метод для одного статуса
    public int countDealsByLastStatusAndUser(User user, String status) {
        LocalDate now = LocalDate.now();
        return dealRepository.countDealsByLastStatusAndUserForMonth(user, status, now.getMonthValue(), now.getYear());
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
        return dealRepository.findByUserAndStatuses(user, statuses);  // Поиск сделок по статусам и пользователю
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
        return dealRepository.findAllDealsByStatuses(statuses);
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
}
