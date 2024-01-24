package com.example.fenikaCRM10.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private DateService() {
        // Приватный конструктор пуст
    }

    public static String getCurrentDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime.format(DATE_FORMATTER);
    }
}
