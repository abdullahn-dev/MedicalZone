package com.example.medicalzone;

public class LabTestCartItem {
    private String name;
    private String price;
    private String quantity;

    // Constructor
    public LabTestCartItem(String name, String price, String quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }
}
