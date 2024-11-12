package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Deal;
import com.example.fenikaCRM10.models.Payments;
import com.example.fenikaCRM10.models.Statuses;
import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.repositories.DealRepository;
import com.example.fenikaCRM10.repositories.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentsService {
    private final PaymentsRepository paymentsRepository;
    private final DealRepository dealRepository;

    public void savePayment(Payments payment, Long dealId) {
        payment.setDealId(dealId);
        payment.setCurrentDate(DateService.getCurrentDate());
        paymentsRepository.save(payment);
    }

    public List<Payments> getPaymentsByDealId(Long dealId) {
        return paymentsRepository.findAllByDealId(dealId);
    }


    private boolean isDealCompletedInCurrentMonth(Deal deal) {
        Optional<Statuses> latestStatus = dealRepository.findLatestStatusByDealId(deal.getDealId());
        if (latestStatus.isPresent()) {
            Statuses status = latestStatus.get();
            YearMonth currentMonth = YearMonth.now();

            // Создаем DateTimeFormatter для формата dd.MM.yyyy HH:mm:ss
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

            // Преобразуем строку в LocalDateTime и затем в YearMonth
            LocalDateTime dateTime = LocalDateTime.parse(status.getCurrentDate(), formatter);
            YearMonth statusMonth = YearMonth.from(dateTime);

            return "Завершен".equals(status.getStatusChoose()) && currentMonth.equals(statusMonth);
        }
        return false;
    }

    public Double getCompanyProfit(Long dealId) {
        Deal deal = dealRepository.findById(dealId).orElse(null);
        if (deal == null || !isDealCompletedInCurrentMonth(deal)) {
            return 0.0;
        }
        return calculateDealCompanyProfit(deal);
    }

    public Double getManagerProfit(Long dealId, User user) {
        double companyProfit = getCompanyProfit(dealId);
        return companyProfit * user.getPercentage() / 100;
    }

    public void deletePaymentById(Long paymentId) {
        paymentsRepository.deleteById(paymentId);
    }

    public Long getDealIdByPaymentId(Long paymentId) {
        Payments payment = paymentsRepository.findById(paymentId).orElse(null);
        return payment != null ? payment.getDealId() : null;
    }

    public double getTotalPaymentsForUser(User user) {
        List<Deal> deals = dealRepository.findByUser(user);
        return deals.stream()
                .filter(this::isDealCompletedInCurrentMonth)
                .mapToDouble(this::calculateTotalPaymentsForDeal)
                .sum();
    }

    public double getCompanyProfitForUser(User user) {
        List<Deal> deals = dealRepository.findByUser(user);
        return deals.stream()
                .filter(this::isDealCompletedInCurrentMonth)
                .mapToDouble(this::calculateDealCompanyProfit)
                .sum();
    }

    public double getManagerProfitForUser(User user) {
        double companyProfit = getCompanyProfitForUser(user);
        return companyProfit * user.getPercentage() / 100;
    }

    public double getTotalPayments(Long dealId) {
        Deal deal = dealRepository.findById(dealId).orElse(null);
        if (deal == null || !isDealCompletedInCurrentMonth(deal)) {
            return 0.0;
        }
        return calculateTotalPaymentsForDeal(deal);
    }

    public double getTotalPaymentsForCompany() {
        return paymentsRepository.findAll().stream()
                .filter(payment -> {
                    Deal deal = dealRepository.findById(payment.getDealId()).orElse(null);
                    return deal != null && isDealCompletedInCurrentMonth(deal) && "Поступление".equals(payment.getStatusPayments());
                })
                .mapToDouble(payment -> Optional.ofNullable(payment.getSum()).orElse(0.0))
                .sum();
    }

    public double getCompanyProfitForCompany() {
        double totalIncome = paymentsRepository.findAll().stream()
                .filter(payment -> {
                    Deal deal = dealRepository.findById(payment.getDealId()).orElse(null);
                    return deal != null && isDealCompletedInCurrentMonth(deal) && "Поступление".equals(payment.getStatusPayments());
                })
                .mapToDouble(payment -> Optional.ofNullable(payment.getSum()).orElse(0.0))
                .sum();

        double totalExpenses = paymentsRepository.findAll().stream()
                .filter(payment -> {
                    Deal deal = dealRepository.findById(payment.getDealId()).orElse(null);
                    return deal != null && isDealCompletedInCurrentMonth(deal) &&
                            ("Расход".equals(payment.getStatusPayments()) || "Налог".equals(payment.getStatusPayments()));
                })
                .mapToDouble(payment -> Optional.ofNullable(payment.getSum()).orElse(0.0))
                .sum();

        return totalIncome - totalExpenses;
    }

    private double calculateDealCompanyProfit(Deal deal) {
        List<Payments> payments = paymentsRepository.findAllByDealId(deal.getDealId());

        double totalIncome = payments.stream()
                .filter(payment -> "Поступление".equals(payment.getStatusPayments()))
                .mapToDouble(payment -> Optional.ofNullable(payment.getSum()).orElse(0.0))
                .sum();

        double totalExpenses = payments.stream()
                .filter(payment -> "Расход".equals(payment.getStatusPayments()) || "Налог".equals(payment.getStatusPayments()))
                .mapToDouble(payment -> Optional.ofNullable(payment.getSum()).orElse(0.0))
                .sum();

        return totalIncome - totalExpenses;
    }

    private double calculateTotalPaymentsForDeal(Deal deal) {
        List<Payments> payments = paymentsRepository.findAllByDealId(deal.getDealId());
        return payments.stream()
                .filter(payment -> "Поступление".equals(payment.getStatusPayments()))
                .mapToDouble(payment -> Optional.ofNullable(payment.getSum()).orElse(0.0))
                .sum();
    }
    public double getPercentage (User user){

        return user.getPercentage();
    }
}
