package com.example.fenikaCRM10.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Statuses {
    private Long id;
    private Long statusId;
    private String statusComment;
    private String currentDate;
    private String statusChoose;;
    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
