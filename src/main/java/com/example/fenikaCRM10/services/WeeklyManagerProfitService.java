package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Deal;
import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.models.WeeklyManagerProfit;
import com.example.fenikaCRM10.repositories.DealRepository;
import com.example.fenikaCRM10.repositories.WeeklyManagerProfitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeeklyManagerProfitService {

    private final WeeklyManagerProfitRepository weeklyManagerProfitRepository;
    private final DealRepository dealRepository;

    public List<WeeklyManagerProfit> getWeeklyProfits(User user, int year, int month) {
        return weeklyManagerProfitRepository.findByUserAndMonthAndYear(user, year, month);
    }

    public void calculateWeeklyProfits(User user, int year, int month) {
        // Находим завершенные сделки за месяц
        List<Deal> completedDeals = dealRepository.findByUserAndStatusAndMonthAndYear(user.getUserId(), "Завершен", year, month);

        // Группируем сделки по неделям
        Map<Integer, Double> weeklyProfits = completedDeals.stream()
                .collect(Collectors.groupingBy(
                        deal -> deal.getCreationDate().get(WeekFields.of(Locale.getDefault()).weekOfMonth()),
                        Collectors.summingDouble(Deal::getThinkSum)
                ));

        // Сохраняем или обновляем данные
        for (Map.Entry<Integer, Double> entry : weeklyProfits.entrySet()) {
            int week = entry.getKey();
            double profit = entry.getValue();

            WeeklyManagerProfit weeklyProfit = weeklyManagerProfitRepository.findByUserAndYearAndMonthAndWeek(user, year, month, week)
                    .orElse(new WeeklyManagerProfit(user, year, month, week, 0.0, 0.0));

            weeklyProfit.setManagerProfit(profit);
            weeklyManagerProfitRepository.save(weeklyProfit);
        }
    }

    public void updatePaidAmount(User user, int year, int month, int week, double paidAmount) {
        WeeklyManagerProfit weeklyProfit = weeklyManagerProfitRepository.findByUserAndYearAndMonthAndWeek(user, year, month, week)
                .orElseThrow(() -> new RuntimeException("Weekly profit record not found"));

        weeklyProfit.setPaidAmount(paidAmount);
        weeklyManagerProfitRepository.save(weeklyProfit);
    }
}
