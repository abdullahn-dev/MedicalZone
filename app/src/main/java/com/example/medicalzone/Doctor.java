package com.example.medicalzone;

public class Doctor {
    private String name;
    private String specialty;
    private String doctorId;

    // Constructor
    public Doctor(String name, String specialty, String doctorId) {
        this.name = name;
        this.specialty = specialty;
        this.doctorId = doctorId;
    }

    public Doctor(String name, String specialty) {
        this.name = name;
        this.specialty = specialty;
        this.doctorId = ""; // Empty string since this will be generated in Firestore
    }

    // Getters and setters
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
