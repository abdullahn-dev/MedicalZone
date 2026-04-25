package com.example.medicalzone;

public class Inventory {
    private String name;
    private String quantity;
    private String price;
    private String expiryDate;  // Add expiryDate field
    private String manufacturer;
    private String description;

    // Constructor
    public Inventory(String name, String quantity, String price, String expiryDate, String manufacturer, String description) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;
        this.manufacturer = manufacturer;
        this.description = description;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getDescription() {
        return description;
    }
}
