package com.example.fenikaCRM10.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comments {
    private Long id;
    private Long commentId;
    private String comment;
    private String currentDate;
    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

}
