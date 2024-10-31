package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.services.CustomUserDetails;
import com.example.fenikaCRM10.services.DealService;
import com.example.fenikaCRM10.services.PaymentsService;
import com.example.fenikaCRM10.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class UserStatisticsController {

    private final DealService dealService;
    private final PaymentsService paymentsService;
    private final UserService userService;

    @GetMapping("/statistics")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUserStatistics(Model model) {
        // Получаем текущего пользователя
        User currentUser = userService.findByPrincipal(
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String userName = currentUser.getName();

        // Проверка, является ли пользователь администратором
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("userName", userName);
        model.addAttribute("isAdmin", isAdmin);

        if (isAdmin) {
            // Суммарная статистика по всем сделкам
            int completedDealsCount = dealService.getTotalCompletedDealsCount();
            int inProgressOrPaidDealsCount = dealService.getTotalInProgressOrPaidDealsCount();
            int refusedDealsCount = dealService.getTotalRefusedDealsCount();
            double totalPayments = paymentsService.getTotalPaymentsForCompany();
            double companyProfit = paymentsService.getCompanyProfitForCompany();

            model.addAttribute("completedDealsCount", completedDealsCount);
            model.addAttribute("inProgressOrPaidDealsCount", inProgressOrPaidDealsCount);
            model.addAttribute("refusedDealsCount", refusedDealsCount);
            model.addAttribute("totalPayments", totalPayments);
            model.addAttribute("companyProfit", companyProfit);

            // Индивидуальная статистика для каждого менеджера
            List<User> users = userService.findAll();
            Map<String, Map<String, Object>> userStatisticsMap = new HashMap<>();
            Map<String, String> userNamesMap = new HashMap<>();

            for (User user : users) {
                int userCompletedDeals = dealService.countDealsByLastStatusAndUser(user, "Завершен");
                int userInProgressOrPaidDeals = dealService.countDealsByStatusesAndUser(user, Arrays.asList("В работе", "Оплачен", "Новая заявка"));
                int userRefusedDeals = dealService.countDealsByLastStatusAndUser(user, "Отказ");
                double userTotalPayments = paymentsService.getTotalPaymentsForUser(user);
                double userCompanyProfit = paymentsService.getCompanyProfitForUser(user);
                double userManagerProfit = paymentsService.getManagerProfitForUser(user);
                double percentage = paymentsService.getPercentage(user);

                Map<String, Object> stats = new HashMap<>();
                stats.put("completedDeals", userCompletedDeals);
                stats.put("inProgressOrPaidDeals", userInProgressOrPaidDeals);
                stats.put("refusedDeals", userRefusedDeals);
                stats.put("totalPayments", userTotalPayments);
                stats.put("companyProfit", userCompanyProfit);
                stats.put("managerProfit", userManagerProfit);
                stats.put("percentage", percentage);

                userStatisticsMap.put(user.getUserId().toString(), stats);
                userNamesMap.put(user.getUserId().toString(), user.getName());
            }

            model.addAttribute("userStatisticsMap", userStatisticsMap);
            model.addAttribute("userNamesMap", userNamesMap);

        } else {
            // Статистика для обычного пользователя
            int completedDealsCount = dealService.countDealsByLastStatusAndUser(currentUser, "Завершен");
            int inProgressOrPaidDealsCount = dealService.countDealsByStatusesAndUser(currentUser, Arrays.asList("В работе", "Оплачен", "Новая заявка"));
            int refusedDealsCount = dealService.countDealsByLastStatusAndUser(currentUser, "Отказ");
            double totalPayments = paymentsService.getTotalPaymentsForUser(currentUser);
            double companyProfit = paymentsService.getCompanyProfitForUser(currentUser);
            double managerProfit = paymentsService.getManagerProfitForUser(currentUser);
            double percentage = paymentsService.getPercentage(currentUser);

            model.addAttribute("completedDealsCount", completedDealsCount);
            model.addAttribute("inProgressOrPaidDealsCount", inProgressOrPaidDealsCount);
            model.addAttribute("refusedDealsCount", refusedDealsCount);
            model.addAttribute("totalPayments", totalPayments);
            model.addAttribute("companyProfit", companyProfit);
            model.addAttribute("managerProfit", managerProfit);
            model.addAttribute("percentage", percentage);
        }

        return "statistics";
    }
}