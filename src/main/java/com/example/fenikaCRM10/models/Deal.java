package com.example.fenikaCRM10.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
//    @Column(name = "numberPhone")
//    private String numberPhone;
    @Column(name = "whatNeed")
    private String whatNeed;
    @Column(name = "deliveryAdress")
    private String deliveryAdress;
//    private String comment;
    @Column(name = "author")
    private String author;
//    @Column(name = "email")
//    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Привязываем сделку к пользователю
    private User user;
    @Transient // Это поле не сохраняется в базу данных
    private double companyProfit;
    @Column(name = "status")
    private String status;
    @Column(name = "lastStatus")
    private String lastStatus;
    @Transient
    private String lastComment;
    @Column(name = "thinkSum")
    private Double thinkSum;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "whereFrom")
    private String whereFrom;
    @Transient
    private String totalPayments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId", referencedColumnName = "clientId")
    private Clients client;
    @Transient
    private String lastStatusDate;
}
