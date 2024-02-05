package com.example.fenikaCRM10.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deal")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deal {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dealId;
    @Column(name = "name")
    private String name;
//    private String nameContact;
    @Column(name = "numberPhone")
    private String numberPhone;
    @Column(name = "whatNeed")
    private String whatNeed;
    @Column(name = "deliveryAdress")
    private String deliveryAdress;
//    private String comment;
    @Column(name = "author")
    private String author;
}
