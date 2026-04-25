package com.example.medicalzone;

public class Appointment {

    private String patientName;
    private String appointmentDate;  // String format for date
    private String appointmentTime;  // String format for time
    private String doctorName;
    private String reason;

    // Constructor
    public Appointment(String patientName, String appointmentDate, String appointmentTime, String doctorName, String reason) {
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.doctorName = doctorName;
        this.reason = reason;
    }

    // Getters
    public String getPatientName() {
        return patientName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getReason() {
        return reason;
    }
}
