package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.Deal;
import com.example.fenikaCRM10.models.Payments;
import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.repositories.PaymentsRepository;
import com.example.fenikaCRM10.services.DealService;
import com.example.fenikaCRM10.services.PaymentsService;
import com.example.fenikaCRM10.services.PaymentsStatusesListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class PaymentsController {
    private final DealService dealService;
    private final PaymentsService paymentsService;
    private final PaymentsRepository paymentsRepository;

    @GetMapping("/payments/{dealId}")
    public String allPayments(Model model, @PathVariable Long dealId) {
        model.addAttribute("dealId", dealService.getDealById(dealId));
        model.addAttribute("allPayments", paymentsService.getPaymentsByDealId(dealId));
        model.addAttribute("listOfStatPay", PaymentsStatusesListService.getPaymentsStatusesList());
        model.addAttribute("earnedMoney", paymentsService.getCompanyProfit(dealId));
        model.addAttribute("moneyOfManager", paymentsService.getManagerProfit(dealId));
        model.addAttribute("currentDate", LocalDate.now());
        return "payments";
    }

    @PostMapping("/savePayment/{dealId}")
    public String savePayment(Payments payment, Model model, @PathVariable Long dealId) {
        Deal deal = dealService.getDealById(dealId); // Получаем сделку
        payment.setDeal(deal);  // Устанавливаем связь платежа со сделкой
        User user = deal.getUser();  // Если у сделки есть связь с пользователем
        payment.setUser(user);
        payment.setCurrentDate(String.valueOf(LocalDate.now()));
        paymentsRepository.save(payment); // Сохраняем платеж
        return "redirect:/payments/" + dealId;
    }

    @PostMapping("/payments/delete/{paymentId}")
    public String deletePayment(@PathVariable Long paymentId) {
        Long dealId = paymentsService.getDealIdByPaymentId(paymentId);
        paymentsService.deletePaymentById(paymentId);
        return "redirect:/payments/" + dealId;
    }
    @GetMapping ("/payments/{dealId}/back")
    public String onBackPressed(@PathVariable Long dealId) {
        return "redirect:/deal-info/" + dealId;
    }
    @GetMapping ("/payments/{dealId}/todeals")
    public String onBackToDealsPressed(@PathVariable Long dealId) {
        return "redirect:/";
    }
}