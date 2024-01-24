package com.example.fenikaCRM10.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class StatusesListService {
    private static List<String> statusesList = new ArrayList<>();
    public StatusesListService(){
        statusesList.add("Новая заявка");
        statusesList.add("В работе");
        statusesList.add("Оплачен");
        statusesList.add("Завершен");
        statusesList.add("Отказ");
    }
    public static List<String> getStatusesList() {
        return statusesList;
    }
}
