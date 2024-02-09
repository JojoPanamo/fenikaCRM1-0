package com.example.fenikaCRM10.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payments {
    @Id
    @Column(name = "paymentId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;
    @Column(name = "dealId")
    private Long dealId;
    @Column(name = "comment_payments")
    private String commentPayments;
    @Column(name = "current_dates")
    private String currentDate;
    @Column(name = "status_payments")
    private String statusPayments;
    @Column(name = "sum")
    private Double sum;
    @Column(name = "earned_money")
    private Double earnedMoney;
    @Column(name = "money_of_manager")
    private Double moneyOfManager;
}
