package com.example.medicalzone;

public class Patient {
    private String name;
    private int age;
    private String illness;  // Removed phone and image fields

    // Default constructor for Firestore
    public Patient() {}

    // Constructor for creating Patient objects
    public Patient(String name, int age, String illness) {
        this.name = name;
        this.age = age;
        this.illness = illness;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }
}
