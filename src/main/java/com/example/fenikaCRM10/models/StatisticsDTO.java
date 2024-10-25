package com.example.fenikaCRM10.models;

import java.util.List;

public class StatisticsDTO {
    private double totalRevenue;  // Общая сумма поступлений
    private double companyProfit; // Прибыль компании
    private double managerProfit; // Прибыль менеджера
    private List<String> completedDeals;  // Список завершенных сделок
    private List<String> ongoingDeals;  // Список текущих сделок

    public StatisticsDTO(double companyProfit, double managerProfit, double totalRevenue,
                         List<String> completedDeals, List<String> ongoingDeals) {
        this.companyProfit = companyProfit;
        this.managerProfit = managerProfit;
        this.totalRevenue = totalRevenue;
        this.completedDeals = completedDeals;
        this.ongoingDeals = ongoingDeals;
    }

    // Геттеры и сеттеры
    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getCompanyProfit() {
        return companyProfit;
    }

    public void setCompanyProfit(double companyProfit) {
        this.companyProfit = companyProfit;
    }

    public double getManagerProfit() {
        return managerProfit;
    }

    public void setManagerProfit(double managerProfit) {
        this.managerProfit = managerProfit;
    }

    public List<String> getCompletedDeals() {
        return completedDeals;
    }

    public void setCompletedDeals(List<String> completedDeals) {
        this.completedDeals = completedDeals;
    }

    public List<String> getOngoingDeals() {
        return ongoingDeals;
    }

    public void setOngoingDeals(List<String> ongoingDeals) {
        this.ongoingDeals = ongoingDeals;
    }
}
