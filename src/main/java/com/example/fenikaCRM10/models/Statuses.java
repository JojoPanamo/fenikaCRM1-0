package com.example.fenikaCRM10.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "statuses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statuses {
    @Id
    @Column(name = "statusId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long statusId;
    @Column(name = "dealId")
    private Long dealId;
    @Column(name = "statusComment")
    private String statusComment;
    @Column(name = "currentDates")
    private String currentDate;
    @Column(name = "statusChoose")
    private String statusChoose;;
    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
