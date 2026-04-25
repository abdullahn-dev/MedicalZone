package com.example.medicalzone;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AppointmentBookingActivity extends AppCompatActivity {

    private Spinner spinnerDoctors;
    private Button btnSelectDate, btnSelectTime, btnBookAppointment;
    private EditText etReason, etPatientName;

    private String selectedDoctor, selectedDate, selectedTime, appointmentReason, patientName;

    private FirebaseFirestore db;
    private ArrayAdapter<String> doctorsAdapter;
    private List<String> doctorsList;

    // Static list of doctors
    private static final Doctor[] doctorsArray = new Doctor[]{
            new Doctor("Dr. John Doe", "Cardiology"),
            new Doctor("Dr. Jane Smith", "Neurology"),
            new Doctor("Dr. Emma Brown", "Orthopedics"),
            new Doctor("Dr. James Johnson", "Dermatology"),
            new Doctor("Dr. Michael Davis", "Pediatrics"),
            new Doctor("Dr. Sarah Wilson", "Gynecology"),
            new Doctor("Dr. David Moore", "Psychiatry"),
            new Doctor("Dr. Maria Garcia", "Ophthalmology"),
            new Doctor("Dr. William Taylor", "Radiology"),
            new Doctor("Dr. Linda Anderson", "Pathology")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booking);

        // Initialize Views
        spinnerDoctors = findViewById(R.id.spinnerDoctors);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSelectTime = findViewById(R.id.btnSelectTime);
        btnBookAppointment = findViewById(R.id.btnBookAppointment);
        etReason = findViewById(R.id.etReason);
        etPatientName = findViewById(R.id.etPatientName);  // Add EditText for patient name

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize doctor list and adapter
        doctorsList = new ArrayList<>();
        for (Doctor doctor : doctorsArray) {
            doctorsList.add(doctor.getName() + " (" + doctor.getSpecialization() + ")");
        }

        doctorsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctorsList);
        doctorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctors.setAdapter(doctorsAdapter);

        // Set up Date Picker
        btnSelectDate.setOnClickListener(view -> openDatePicker());

        // Set up Time Picker
        btnSelectTime.setOnClickListener(view -> openTimePicker());

        // Book Appointment Button
        btnBookAppointment.setOnClickListener(view -> bookAppointment());
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    btnSelectDate.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void openTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute1) -> {
                    selectedTime = String.format("%02d:%02d", hourOfDay, minute1);
                    btnSelectTime.setText(selectedTime);
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void bookAppointment() {
        // Get input from EditText
        patientName = etPatientName.getText().toString().trim();
        selectedDoctor = (String) spinnerDoctors.getSelectedItem();
        appointmentReason = etReason.getText().toString().trim();

        // Validate input
        if (patientName.isEmpty() || selectedDoctor == null || selectedDate == null || selectedTime == null || appointmentReason.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields to book an appointment", Toast.LENGTH_SHORT).show();
            return;
        }

        // Extract the doctor's name from the selected string
        String doctorName = selectedDoctor.split(" \\(")[0];  // Split at the first '(' to get the doctor name

        // Create the confirmation dialog
        new android.app.AlertDialog.Builder(this)
                .setTitle("Confirm Appointment")
                .setMessage("Are you sure you want to book an appointment with " + doctorName + " on " + selectedDate + " at " + selectedTime + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Proceed with booking the appointment

                    // Generate a unique appointment ID
                    String appointmentId = db.collection("appointments").document().getId();

                    // Create an appointment object
                    Appointment appointment = new Appointment(
                            doctorName,    // The doctor's name
                            selectedDate,  // The selected date
                            selectedTime,  // The selected time
                            appointmentReason,  // The reason for the appointment
                            patientName     // Use the actual patient name
                    );

                    // Save the appointment in Firestore
                    db.collection("appointments").document(appointmentId)
                            .set(appointment)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(AppointmentBookingActivity.this, "Appointment booked successfully!", Toast.LENGTH_SHORT).show();
                                finish();  // Close the activity after successful booking
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(AppointmentBookingActivity.this, "Failed to book appointment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Do nothing (appointment not booked)
                    dialog.dismiss();
                })
                .setCancelable(false)  // Prevent dismissal by tapping outside
                .show();
    }

    // Doctor class for static data
    public static class Doctor {
        private String name;
        private String specialization;

        public Doctor(String name, String specialization) {
            this.name = name;
            this.specialization = specialization;
        }

        public String getName() {
            return name;
        }

        public String getSpecialization() {
            return specialization;
        }
    }

    // Appointment class for storing appointment data
    public static class Appointment {
        private String doctorName;
        private String date;
        private String time;
        private String reason;
        private String patientName;

        public Appointment(String doctorName, String date, String time, String reason, String patientName) {
            this.doctorName = doctorName;
            this.date = date;
            this.time = time;
            this.reason = reason;
            this.patientName = patientName;
        }

        // Getters and setters for appointment data
        public String getDoctorName() {
            return doctorName;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getReason() {
            return reason;
        }

        public String getPatientName() {
            return patientName;
        }
    }
}
