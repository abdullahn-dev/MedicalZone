package com.example.medicalzone;

public class Medicine {
    private String name;
    private String quantity;
    private String price;
    private String expiryDate;
    private String manufacturer;
    private String description;

    // Default constructor (required for Firestore)
    public Medicine() {}

    public Medicine(String name, String quantity, String price, String expiryDate, String manufacturer, String description) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;
        this.manufacturer = manufacturer;
        this.description = description;
    }

    // Getters and setters for Firestore
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
