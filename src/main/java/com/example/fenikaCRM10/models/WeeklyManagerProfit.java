package com.example.fenikaCRM10.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "weekly_manager_profits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyManagerProfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private int week;

    @Column(name = "manager_profit", nullable = false)
    private double managerProfit; // Заработок менеджера за неделю

    @Column(name = "paid_amount", nullable = false)
    private double paidAmount; // Выплаченная сумма менеджеру

    // Кастомный конструктор
    public WeeklyManagerProfit(User user, int year, int month, int week, double managerProfit, double paidAmount) {
        this.user = user;
        this.year = year;
        this.month = month;
        this.week = week;
        this.managerProfit = managerProfit;
        this.paidAmount = paidAmount;
    }
}
