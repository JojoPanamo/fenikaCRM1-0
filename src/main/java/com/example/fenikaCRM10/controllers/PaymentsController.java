package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.Payments;
import com.example.fenikaCRM10.services.DealService;
import com.example.fenikaCRM10.services.PaymentsService;
import com.example.fenikaCRM10.services.PaymentsStatusesListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PaymentsController {
    private final DealService dealService;
    private final PaymentsService paymentsService;

    @GetMapping("/payments/{id}")
    public String allPayments(Model model, @PathVariable Long id) {
        model.addAttribute("dealId", dealService.getDealById(id));
        model.addAttribute("allPayments", paymentsService.getPaymentsByDealId(id));
        model.addAttribute("listOfStatPay", PaymentsStatusesListService.getPaymentsStatusesList());
        model.addAttribute("earnedMoney", paymentsService.getCompanyProfit(id));
        model.addAttribute("moneyOfManager", paymentsService.getManagerProfit(id));
        return "payments";
    }

    @PostMapping("/savePayment/{id}")
    public String savePayment(Payments payment, Model model, @PathVariable Long id) {
        paymentsService.savePayment(payment, id);

        return "redirect:/payments/" + id;
    }
}