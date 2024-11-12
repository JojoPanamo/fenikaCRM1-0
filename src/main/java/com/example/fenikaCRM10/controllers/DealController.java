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
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DealController {
    private final DealService dealService;
    private final UserService userService;
    private final DealRepository dealRepository;
    private final PaymentsService paymentsService;
    private final StatusesService statusesService;

    //    @GetMapping("/deals/")
//    public String deals(@RequestParam(name = "name", required = false) String name, Model model) {
//        model.addAttribute("deals", dealService.listDeals(name));
//        return "deals";
//    }
    @GetMapping("/deals")
    public String getUserDeals(
            @RequestParam(name = "statusFilter", required = false, defaultValue = "Новая заявка,В работе,Оплачен") String statusFilter,
            Model model) {
        // Получаем текущего пользователя
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetails.getId());

        // Преобразуем строку фильтров в список статусов
        List<String> statuses = Arrays.asList(statusFilter.split(","));

        List<Deal> deals;

        // Проверка роли: если администратор, то получить все сделки
        if (currentUser.getRoles().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            deals = dealService.findDealsByStatusesForAdmin(statuses); // Метод для всех сделок с фильтрацией
        } else {
            // Иначе — только сделки текущего пользователя
            deals = dealService.findDealsByStatuses(currentUser, statuses);
        }

        // Заполнение данных по сделкам
        for (Deal deal : deals) {
            deal.setCompanyProfit(paymentsService.getCompanyProfit(deal.getDealId()));

            // Получаем последний статус сделки
            String lastStatus = statusesService.getLastStatusForDeal(deal.getDealId());
            deal.setLastStatus(lastStatus);
        }

        model.addAttribute("deals", deals);
        model.addAttribute("selectedStatuses", statuses); // Добавляем выбранные статусы для отображения
        return "deals"; // Отображаем страницу со списком сделок
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
        CustomUserDetails userDetailsInfo = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetailsInfo.getId());
        List<Deal> userDeals = dealService.findByUser(currentUser);
        for (Deal deal : userDeals) {
            deal.setCompanyProfit(paymentsService.getCompanyProfit(deal.getDealId()));
        }
        model.addAttribute("deal", dealService.getDealById(dealId));
        return "deal-info";
    }
    @GetMapping("/deal-create")
    public String dealCreatePAge(Model model) {
        model.addAttribute("whereFromOptions", DealServiceList.getAuthors());
        model.addAttribute("authors", DealServiceList.getAuthors());
//        model.addAttribute("deal", dealService.listDeals());
        return "deal-create";
    }
    @GetMapping ("/deal-info/{dealId}/back")
    public String onBackPressed(@PathVariable Long dealId) {
        return "redirect:/";
    }
    @GetMapping ("/deal-create/back")
    public String onBackPressed1() {
        return "redirect:/";
    }
}
