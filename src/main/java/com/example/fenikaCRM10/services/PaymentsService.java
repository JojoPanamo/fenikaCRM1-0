package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Payments;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentsService {
    private Map<Long, List<Payments>> paymentsByDeal = new HashMap<>();
    private Map<Long, Double> companyProfitByDeal = new HashMap<>();
    private Map<Long, Double> managerProfitByDeal = new HashMap<>();

    public void savePayment(Payments payment, Long dealId) {
        payment.setDealId(dealId);
        payment.setCurrentDate(DateService.getCurrentDate());
        paymentsByDeal.computeIfAbsent(dealId, k -> new ArrayList<>()).add(payment);
        calculateProfits(dealId);
    }

    public List<Payments> getPaymentsByDealId(Long dealId) {
        return paymentsByDeal.getOrDefault(dealId, Collections.emptyList());
    }

    public Double getCompanyProfit(Long dealId) {
        return companyProfitByDeal.getOrDefault(dealId, 0.0);
    }

    public Double getManagerProfit(Long dealId) {
        return managerProfitByDeal.getOrDefault(dealId, 0.0);
    }

    private void calculateProfits(Long dealId) {
        List<Payments> payments = paymentsByDeal.getOrDefault(dealId, Collections.emptyList());
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
        double managerProfit = companyProfit * 0.25;

        companyProfitByDeal.put(dealId, companyProfit);
        managerProfitByDeal.put(dealId, managerProfit);
    }
}
