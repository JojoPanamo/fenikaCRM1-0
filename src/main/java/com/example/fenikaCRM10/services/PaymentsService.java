package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Payments;
import com.example.fenikaCRM10.models.User;
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

        log.info("Полученные платежи для сделки {}: {}", dealId, payments);

        for (Payments payment : payments) {
            String status = payment.getStatusPayments();
            Double sum = payment.getSum();

            if (sum == null) {
                log.warn("Платеж со статусом {} имеет нулевую сумму", status);
                continue;
            }

            log.info("Обработка платежа: Статус: {}, Сумма: {}", status, sum);

            if ("Поступление".equals(status)) {
                companyProfit += sum;
            } else if ("Расход".equals(status) || "Налог".equals(status)) {
                companyProfit -= sum;
            }
        }

        log.info("Итоговая прибыль компании для сделки {}: {}", dealId, companyProfit);

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

    public double getTotalPaymentsForUser(User user) {
        List<Payments> payments = paymentsRepository.findByUser(user);
        return payments.stream()
                .filter(payment -> "Поступление".equals(payment.getStatusPayments())) // Фильтруем только поступления
                .mapToDouble(payment -> Optional.ofNullable(payment.getSum()).orElse(0.0))
                .sum();
    }

    public double getCompanyProfitForUser(User user) {
        // Получаем все платежи пользователя
        List<Payments> payments = paymentsRepository.findByUser(user);

        // Суммируем все поступления
        double totalIncome = payments.stream()
                .filter(payment -> "Поступление".equals(payment.getStatusPayments())) // Фильтруем только поступления
                .mapToDouble(payment -> Optional.ofNullable(payment.getSum()).orElse(0.0)) // Если сумма null, заменяем 0
                .sum();

        // Суммируем все расходы
        double totalExpenses = payments.stream()
                .filter(payment -> "Расход".equals(payment.getStatusPayments())) // Фильтруем только расходы
                .mapToDouble(payment -> Optional.ofNullable(payment.getSum()).orElse(0.0)) // Если сумма null, заменяем 0
                .sum();

        // Логируем суммы для проверки
        log.info("Общая сумма поступлений: {}", totalIncome);
        log.info("Общая сумма расходов: {}", totalExpenses);

        // Возвращаем прибыль (поступления - расходы)
        return totalIncome - totalExpenses;
    }


    public double getManagerProfitForUser(User user) {
        double companyProfit = getCompanyProfitForUser(user);

        // Логируем прибыль компании
        log.info("Прибыль компании для пользователя {}: {}", user.getEmail(), companyProfit);

        double managerProfit = companyProfit * 0.25;

        // Логируем прибыль менеджера
        log.info("Прибыль менеджера для пользователя {}: {}", user.getEmail(), managerProfit);

        return managerProfit;
    }
    public double getTotalPayments(Long dealId) {
        // Получаем все платежи по конкретной сделке
        List<Payments> payments = paymentsRepository.findAllByDealId(dealId);

        // Суммируем все поступления по сделке
        return payments.stream()
                .filter(payment -> "Поступление".equals(payment.getStatusPayments())) // Фильтруем только поступления
                .mapToDouble(payment -> Optional.ofNullable(payment.getSum()).orElse(0.0)) // Если сумма null, заменяем 0
                .sum();
    }
}

