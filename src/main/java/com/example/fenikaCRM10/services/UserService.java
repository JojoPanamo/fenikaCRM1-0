package com.example.fenikaCRM10.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static List<String> authors = new ArrayList<>();
    public UserService(){
        authors.add("Cветлана");
        authors.add("Георгий");
        authors.add("Наталия");
    }
    public static List<String> getAuthors() {
        return authors;
    }
}
