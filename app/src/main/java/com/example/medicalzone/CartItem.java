package com.example.medicalzone;

public class CartItem {

    private String name;
    private double price;
    private String description;
    private long quantity;

    public CartItem(String name, double price, String description, long quantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public long getQuantity() {
        return quantity;
    }
}
