package com.example.medicalzone;

public class Patientt {

    private String name;
    private String email;
    private String illness;
    private String id;

    // Default constructor for Firestore
    public Patientt() {}

    public Patientt(String name, String email, String illness) {
        this.name = name;
        this.email = email;
        this.illness = illness;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
