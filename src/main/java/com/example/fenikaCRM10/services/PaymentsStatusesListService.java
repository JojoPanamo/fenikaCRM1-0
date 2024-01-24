package com.example.fenikaCRM10.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentsStatusesListService {
    private static List<String> paymentsStatusesList = new ArrayList<>();
    public PaymentsStatusesListService(){
        paymentsStatusesList.add("Поступление");
        paymentsStatusesList.add("Расход");
        paymentsStatusesList.add("Налог");
    }
    public static List<String> getPaymentsStatusesList() {
        return paymentsStatusesList;
    }
}
