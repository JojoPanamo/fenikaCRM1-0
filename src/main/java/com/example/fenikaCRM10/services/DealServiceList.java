package com.example.fenikaCRM10.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealServiceList {
    private static List<String> whereFromList = new ArrayList<>();
    public DealServiceList(){
        whereFromList.add("Сайт");
        whereFromList.add("Поля");
        whereFromList.add("Авито");
        whereFromList.add("С улицы");
        whereFromList.add("По рекомендации");
        whereFromList.add("Повторное обращение");
    }
    public static List<String> getAuthors() {
        return whereFromList;
    }
}
