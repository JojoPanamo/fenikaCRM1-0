package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.Clients;
import com.example.fenikaCRM10.models.Deal;
import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.repositories.DealRepository;
import com.example.fenikaCRM10.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class DealController {
    private final DealService dealService;
    private final UserService userService;
    private final DealRepository dealRepository;
    private final PaymentsService paymentsService;
    private final StatusesService statusesService;
    private final CommentService commentService;
    private final ClientService clientService;

    //    @GetMapping("/deals/")
//    public String deals(@RequestParam(name = "name", required = false) String name, Model model) {
//        model.addAttribute("deals", dealService.listDeals(name));
//        return "deals";
//    }

    @GetMapping("/deals")
    public String getUserDeals(
            @RequestParam(name = "statusFilter", required = false, defaultValue = "Новая заявка,В работе,Оплачен") String statusFilter,
            @RequestParam(name = "userId", required = false) Long userId, // Для фильтрации по пользователю
            Model model) {

        // Получение текущего пользователя
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetails.getId());

        // Преобразование строки статусов в список
        List<String> statuses = Arrays.asList(statusFilter.split(","));
        List<Deal> deals;

        // Проверка роли администратора
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            if (userId != null) {
                // Если выбран конкретный пользователь, фильтруем сделки по пользователю и статусам
                User selectedUser = userService.findById(userId);
                deals = dealService.findDealsByStatuses(selectedUser, statuses);
            } else {
                // Если пользователь не выбран, фильтруем только по статусам
                deals = dealService.findDealsByStatusesForAdmin(statuses);
            }
        } else {
            // Обычный пользователь видит только свои сделки с фильтрацией по статусам
            deals = dealService.findDealsByStatuses(currentUser, statuses);
        }

        if (statuses.contains("Завершен")) {
            deals.sort((d1, d2) -> {
                if ("Завершен".equals(d1.getLastStatus()) && "Завершен".equals(d2.getLastStatus())) {
                    return d2.getLastStatusDate().compareTo(d1.getLastStatusDate());
                }
                return 0; // Сохраняем порядок для остальных сделок
            });
        }

        // Формат для суммы
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("ru", "RU"));

        // Заполняем данные по сделкам
        for (Deal deal : deals) {
            String lastStatus = statusesService.getLastStatusForDeal(deal.getDealId());
            String lastStatusDate = statusesService.getLastStatusDateForDeal(deal.getDealId());
            deal.setLastStatus(lastStatus != null ? lastStatus : "Статус не установлен");
            deal.setLastStatusDate(lastStatusDate != null ? lastStatusDate : "Дата не установлена");

            // Заполнение суммы поступлений с форматированием
            double totalPayments = paymentsService.getTotalPaymentsInside(deal.getDealId());
            String formattedTotalPayments = numberFormat.format(totalPayments) + " руб.";
            deal.setTotalPayments(formattedTotalPayments);
        }


        Clients client = null;

        // Добавляем атрибуты для модели
        model.addAttribute("clients", client);
        model.addAttribute("deals", deals);
        model.addAttribute("selectedStatuses", statuses);
        model.addAttribute("isAdmin", isAdmin);

        if (isAdmin) {
            model.addAttribute("users", userService.findAll()); // Список пользователей для фильтра
            model.addAttribute("selectedUserId", userId != null ? userId : ""); // Передаем ID выбранного пользователя в модель
        }

        return "deals"; // Возвращаем страницу сделок
    }

    @PostMapping("/deal-create-save")
    public String createDealSave(@RequestParam String clientName,
                                 @RequestParam String phoneNumber,
                                 @RequestParam String email,
                                 @RequestParam(required = false) Long clientId,
                                 @ModelAttribute Deal deal,
                                 Principal principal) {
        Clients client;
        if (clientId != null) {
            // Если ID клиента существует, ищем клиента в базе
            client = clientService.getClientById(clientId)
                    .orElseThrow(() -> new RuntimeException("Клиент не найден"));
        } else {
            // Если ID клиента нет, создаем нового клиента
            client = clientService.findOrCreateClient(clientName, phoneNumber, email);
        }
        deal.setClient(client);


        // Получаем текущего пользователя
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetails.getId());
        deal.setUser(currentUser);
        deal.setAuthor(currentUser.getName());
        client.setUser(currentUser);

        // Сохраняем сделку
        dealService.saveDeal(deal);

        return "redirect:/deals";
    }

//    @PostMapping("/deal-create")
//    public String createDealSave(@ModelAttribute Deal deal, Principal principal) {
//        // Получаем текущего пользователя из контекста безопасности
//        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        // Получаем ID пользователя
//        Long userId = userDetails.getId(); // Предположим, что в вашем CustomUserDetails есть метод getId()
//
//        // Ищем пользователя по ID и устанавливаем его для сделки
//        User currentUser = userService.findById(userId);
//
//        // Устанавливаем пользователя для сделки
//        deal.setUser(currentUser);
//
//        // Сохраняем сделку
//        dealService.saveDeal(deal);
//
//        return "redirect:/deals/";
//    }
//    @GetMapping("/")
//    public String dealByName(@RequestParam(name = "name", required = false) String name, Model model){
//        model.addAttribute("dealByName", dealService.dealByName(name));
//        return "deals";
//    }

//    @PostMapping("/deal-info/deal/delete/{id}")
//    public String deleteDeal(@PathVariable Long id) {
//        dealService.deleteDeal(id);
//        return "redirect:/";
//    }

    @GetMapping("deal-info/{dealId}")
    public String dealInfo(@PathVariable Long dealId, Model model) {
        // Получение текущего пользователя
        CustomUserDetails userDetailsInfo = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetailsInfo.getId());

        // Проверка роли администратора
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        // Получение данных о сделке
        Deal deal = dealService.getDealById(dealId);

        // Получение последнего статуса
        String lastStatus = statusesService.getLastStatusForDeal(dealId);
        deal.setLastStatus(lastStatus != null ? lastStatus : "Статус не установлен");

        String lastComment = commentService.getLastCommentForDeal(dealId);
        deal.setLastComment(lastComment != null ? lastComment : "Комментариев пока нет");

        Double thinkSum = deal.getThinkSum() != null ? deal.getThinkSum() : 0.0; // Если null, заменяем на 0
        model.addAttribute("thinkSum", thinkSum);

        // Подсчет прибыли компании
        deal.setCompanyProfit(paymentsService.getCompanyProfit(dealId));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("deal", deal);

        return "deal-info";
    }
    @PostMapping("/update-think-sum")
    public String updateThinkSum(@RequestParam("dealId") Long dealId,
                                 @RequestParam(value = "thinkSum", required = false) Double thinkSum, Model model) {
        CustomUserDetails userDetailsInfo = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetailsInfo.getId());

        // Проверка роли администратора
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        // Получаем текущую сделку
        Deal deal = dealService.getDealById(dealId);

        // Если `thinkSum` не передан, используем текущее значение из базы
        if (thinkSum == null) {
            thinkSum = deal.getThinkSum();
        }

        // Обновляем сумму через сервис
        dealService.updateThinkSum(dealId, thinkSum);

        model.addAttribute("isAdmin", isAdmin);

        // Перенаправляем обратно на страницу информации о сделке
        return "redirect:/deal-info/" + dealId;
    }


    @GetMapping("/deal-create")
    public String dealCreatePAge(Model model) {
        User currentUser = userService.findByPrincipal(
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        String userName = currentUser.getName();
        Clients client = new Clients();
        client.setClientName(null);
        client.setPhoneNumber(null);
        client.setEmail(null);
        client.setClientContact(null);
        client.setUser(currentUser);

        model.addAttribute("client", client);
        model.addAttribute("query", "");
        model.addAttribute("userName", userName);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("whereFromOptions", DealServiceList.getAuthors());
        model.addAttribute("authors", DealServiceList.getAuthors());
//        model.addAttribute("deal", dealService.listDeals());
        return "deal-create";
    }
    @GetMapping ("/deal-info/{dealId}/back")
    public String onBackPressed(@PathVariable Long dealId) {
        return "redirect:/deals";
    }
    @GetMapping ("/deal-create/back")
    public String onBackPressed1() {
        return "redirect:/";
    }

    public List<Deal> findDealsByUser(Long userId) {
        return dealRepository.findByUser_UserId(userId);
    }


}
