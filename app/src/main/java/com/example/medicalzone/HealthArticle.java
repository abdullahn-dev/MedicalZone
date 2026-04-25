package com.example.medicalzone;

public class HealthArticle {

    private String title;
    private String pdfUrl;
    private String description;

    public HealthArticle(String title, String pdfUrl, String description) {
        this.title = title;
        this.pdfUrl = pdfUrl;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public String getDescription() {
        return description;
    }
}
