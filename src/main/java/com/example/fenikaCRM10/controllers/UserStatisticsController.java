package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.services.CustomUserDetails;
import com.example.fenikaCRM10.services.DealService;
import com.example.fenikaCRM10.services.PaymentsService;
import com.example.fenikaCRM10.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserStatisticsController {

    private final DealService dealService;
    private final PaymentsService paymentsService;
    private final UserService userService;

    @GetMapping("/statistics")
    public String getUserStatistics(Model model) {
        // Получаем текущего пользователя
        User currentUser = userService.findByPrincipal(
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        // Количество сделок по статусам
        List<String> statuses = Arrays.asList("В работе", "Оплачен", "Новая заявка");
        int inProgressOrPaidDealsCount = dealService.countDealsByStatusesAndUser(currentUser, statuses);
        int refusedDealsCount = dealService.countDealsByLastStatusAndUser( currentUser, "Отказ");
        int completedDealsCount = dealService.countDealsByLastStatusAndUser(currentUser, "Завершен");


        // Общая сумма поступлений
        double totalPayments = paymentsService.getTotalPaymentsForUser(currentUser);

        // Прибыль компании и менеджера
        double companyProfit = paymentsService.getCompanyProfitForUser(currentUser);  // динамически
        double managerProfit = paymentsService.getManagerProfitForUser(currentUser);

        // Добавляем атрибуты в модель
        model.addAttribute("completedDealsCount", completedDealsCount);
        model.addAttribute("inProgressOrPaidDealsCount", inProgressOrPaidDealsCount);
        model.addAttribute("refusedDealsCount", refusedDealsCount);
        model.addAttribute("totalPayments", totalPayments);
        model.addAttribute("companyProfit", Optional.ofNullable(companyProfit).orElse(0.0));
        model.addAttribute("managerProfit", Optional.ofNullable(managerProfit).orElse(0.0));



        return "statistics";
    }
}

