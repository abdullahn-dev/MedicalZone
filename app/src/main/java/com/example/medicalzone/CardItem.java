package com.example.medicalzone;

public class CardItem {

    private int imageResource;
    private String title;

    // Constructor
    public CardItem(int imageResource, String title) {
        this.imageResource = imageResource;
        this.title = title;
    }

    // Getters
    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }
}
