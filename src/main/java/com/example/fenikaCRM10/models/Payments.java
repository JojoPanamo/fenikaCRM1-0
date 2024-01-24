package com.example.fenikaCRM10.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payments {
    private Long id;
    private Long dealId;
    private String commentPayments;
    private String currentDate;
    private String statusPayments;
    private Double sum;
    private Double earnedMoney;
    private Double moneyOfManager;
}
