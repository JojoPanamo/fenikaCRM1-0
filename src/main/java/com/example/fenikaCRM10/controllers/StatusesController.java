package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.Statuses;
import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class StatusesController {
    private final StatusesService statusesService;
    private final DealService dealService;
    private final UserService userService;

    @GetMapping("/statuses/{dealId}")
    public String allStatuses(Model model, @PathVariable Long dealId) {
        User currentUser = userService.findByPrincipal(
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("dealId", dealService.getDealById(dealId));
        model.addAttribute("allStatuses", statusesService.getStatusesByDealId(dealId));
        model.addAttribute("statusList", StatusesListService.getStatusesList());
        return "statuses";
    }

    @PostMapping("/saveStatus/{dealId}")
    public String saveStatus(Statuses statuses, @PathVariable Long dealId) {
        statusesService.saveStatus(statuses, dealId);
        return "redirect:/statuses/" + dealId;
    }
    @GetMapping ("/statuses/{dealId}/back")
    public String onBackPressed(@PathVariable Long dealId) {
        return "redirect:/deal-info/" + dealId;
    }
    @GetMapping ("/statuses/{dealId}/todeals")
    public String onBackToDealsPressed(@PathVariable Long dealId) {
        return "redirect:/";
    }
}
