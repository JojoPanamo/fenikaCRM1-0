package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.models.WeeklyManagerProfit;
import com.example.fenikaCRM10.services.CustomUserDetails;
import com.example.fenikaCRM10.services.UserService;
import com.example.fenikaCRM10.services.WeeklyManagerProfitService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WeeklyManagerProfitController {

    private final WeeklyManagerProfitService weeklyManagerProfitService;
    private final UserService userService;

    @GetMapping("/weekly-manager-profit/{year}/{month}")
    public String getWeeklyProfits(@PathVariable int year, @PathVariable int month, Model model, Principal principal) {
        User currentUser = userService.findByPrincipal(
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<WeeklyManagerProfit> profits = weeklyManagerProfitService.getWeeklyProfits(currentUser, year, month);

        model.addAttribute("profits", profits);
        model.addAttribute("currentYear", year);
        model.addAttribute("currentMonth", month);

        return "weekly-manager-profit";
    }

    @PostMapping("/weekly-manager-profit/update")
    public String updatePaidAmount(@RequestParam Long userId, @RequestParam int year, @RequestParam int month,
                                   @RequestParam int week, @RequestParam double paidAmount) {
        User user = userService.findById(userId);
        weeklyManagerProfitService.updatePaidAmount(user, year, month, week, paidAmount);
        return "redirect:/weekly-manager-profit/" + year + "/" + month;
    }
}
