package com.example.fenikaCRM10.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Deal {
    private Long id;
    private String name;
//    private String nameContact;
    private String numberPhone;
    private String whatNeed;
    private String deliveryAdress;
//    private String comment;
    private String author;
}
