package com.example.medicalzone;

public class MedicineCartItem {
    private String name;
    private String price;
    private String quantity;

    public MedicineCartItem(String name, String price, String quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

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
