package com.example.fenikaCRM10.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientInfo {
    private Long id;
    private String name;
    private String numberPhone;
    private String whatNeed;
    private String deliveryAdress;
    private String vendorNumberPayment;
    private String ourNumberPayment;
    private double clientPayment;
    private double vendorPayment;
    private double marja = clientPayment - vendorPayment;
    private String comment;
    private String author;
}
