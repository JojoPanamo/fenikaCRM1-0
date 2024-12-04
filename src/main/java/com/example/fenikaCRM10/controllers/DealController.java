package com.example.fenikaCRM10.controllers;

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

        // Формат для суммы
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("ru", "RU"));

        // Заполняем данные по сделкам
        for (Deal deal : deals) {
            String lastStatus = statusesService.getLastStatusForDeal(deal.getDealId());
            deal.setLastStatus(lastStatus != null ? lastStatus : "Статус не установлен");

            // Заполнение суммы поступлений с форматированием
            double totalPayments = paymentsService.getTotalPaymentsInside(deal.getDealId());
            String formattedTotalPayments = numberFormat.format(totalPayments) + " руб.";
            deal.setTotalPayments(formattedTotalPayments);
        }

        // Добавляем атрибуты для модели
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
    public String createDealSave(@ModelAttribute Deal deal, Principal principal) {
        // Получаем текущего пользователя
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetails.getId());

        // Устанавливаем пользователя сделки
        deal.setUser(currentUser);

        // Устанавливаем автора сделки
        deal.setAuthor(currentUser.getName());

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
                                 @RequestParam("thinkSum") Double thinkSum, Model model) {
        CustomUserDetails userDetailsInfo = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetailsInfo.getId());

        // Проверка роли администратора
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
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
