package com.example.medicalzone;

public class User {
    private String username;
    private String email;

    // Default constructor (required for Firestore)
    public User() {
        // Firestore requires a default constructor
    }

    // Constructor with both username and email
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Getters for username and email
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
