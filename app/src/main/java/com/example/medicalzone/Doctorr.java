package com.example.medicalzone;

public class Doctorr {
    private String id;
    private String name;
    private String specialty;

    // Default constructor for Firestore
    public Doctorr() {
    }

    // Constructor with all fields
    public Doctorr(String id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
