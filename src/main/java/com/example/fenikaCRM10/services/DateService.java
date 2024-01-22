package com.example.fenikaCRM10.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateService() {
        // Приватный конструктор пуст
    }

    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(DATE_FORMATTER);
    }
}
