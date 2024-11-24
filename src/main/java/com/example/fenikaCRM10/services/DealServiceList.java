package com.example.fenikaCRM10.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealServiceList {
    private static List<String> whereFromList = new ArrayList<>();
    public DealServiceList(){
        whereFromList.add("САЙТ");
        whereFromList.add("ПОЛЯ");
        whereFromList.add("АВИТО");
        whereFromList.add("С УЛИЦЫ");
        whereFromList.add("РЕКОМЕНДАЦИЯ");
        whereFromList.add("ПОВТОРНОЕ ОБРАЩЕНИЕ");
    }
    public static List<String> getAuthors() {
        return whereFromList;
    }
}
