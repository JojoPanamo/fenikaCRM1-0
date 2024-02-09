package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Payments;
import com.example.fenikaCRM10.repositories.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentsService {
    private final PaymentsRepository paymentsRepository;

    public void savePayment(Payments payment, Long dealId) {
        payment.setDealId(dealId);
        payment.setCurrentDate(DateService.getCurrentDate());
        paymentsRepository.save(payment);
    }

    public List<Payments> getPaymentsByDealId(Long dealId) {
        return paymentsRepository.findAllByDealId(dealId);
    }

    public Double getCompanyProfit(Long dealId) {
        List<Payments> payments = paymentsRepository.findAllByDealId(dealId);
        double companyProfit = 0.0;

        for (Payments payment : payments) {
            if ("Поступление".equals(payment.getStatusPayments())) {
                companyProfit += payment.getSum();
            } else if ("Расход".equals(payment.getStatusPayments())) {
                companyProfit -= payment.getSum();
            } else if ("Налог".equals(payment.getStatusPayments())) {
                companyProfit -= payment.getSum();
            }
        }

        return companyProfit;
    }

    public Double getManagerProfit(Long dealId) {
        double companyProfit = getCompanyProfit(dealId);
        return companyProfit * 0.25;
    }

    public void deletePaymentById(Long paymentId) {
        paymentsRepository.deleteById(paymentId);
    }
    public Long getDealIdByPaymentId(Long paymentId) {
        Payments payment = paymentsRepository.findById(paymentId).orElse(null);
        if (payment != null) {
            return payment.getDealId();
        } else {
            return null;
        }
    }
}
