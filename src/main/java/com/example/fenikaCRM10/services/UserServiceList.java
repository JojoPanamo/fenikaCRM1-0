package com.example.fenikaCRM10.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceList {
    private static List<String> authorsList = new ArrayList<>();
    public UserServiceList(){
        authorsList.add("Cветлана");
        authorsList.add("Георгий");
        authorsList.add("Наталия");
    }
    public static List<String> getAuthors() {
        return authorsList;
    }
}
