package com.example.medicalzone;

public class SalesSummary {
    private double totalBill;
    private double profit;
    private double actualSales;

    public SalesSummary() {
    }

    public SalesSummary(double totalBill, double profit, double actualSales) {
        this.totalBill = totalBill;
        this.profit = profit;
        this.actualSales = actualSales;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getActualSales() {
        return actualSales;
    }

    public void setActualSales(double actualSales) {
        this.actualSales = actualSales;
    }
}
