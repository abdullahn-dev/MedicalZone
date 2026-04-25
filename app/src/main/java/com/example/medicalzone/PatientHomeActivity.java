package com.example.medicalzone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PatientHomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DoctorWithoutImageAdapter adapter;
    List<Doctor> doctorList;
    private FloatingActionButton fabAppointment;
    private FloatingActionButton fabCart;

    // UI components for quick access cards
    private MaterialCardView cardAppointments, cardBuyMedicines, cardLabTests, cardHealthTips, cardChatbot, cardViewReports;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_patient);

        // Initialize RecyclerView for doctors
        recyclerView = findViewById(R.id.recyclerDoctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Floating Action Button for appointment
        fabAppointment = findViewById(R.id.fabAppointment);
        fabAppointment.setOnClickListener(v -> {
            Intent intent = new Intent(PatientHomeActivity.this, AppointmentBookingActivity.class);
            startActivity(intent);
        });

        fabCart = findViewById(R.id.fabCart);
        fabCart.setOnClickListener(v -> {
            Intent intent = new Intent(PatientHomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // Initialize quick access cards
        cardAppointments = findViewById(R.id.cardAppointments);
        cardBuyMedicines = findViewById(R.id.cardBuyMedicines);
        cardLabTests = findViewById(R.id.cardLabTests);
        cardHealthTips = findViewById(R.id.cardHealthTips);
        cardChatbot = findViewById(R.id.cardChatbot);
        cardViewReports = findViewById(R.id.cardViewReports);

        // Set click listeners for the cards
        cardAppointments.setOnClickListener(v -> openAppointmentsActivity());
        cardBuyMedicines.setOnClickListener(v -> openBuyMedicinesActivity());
        cardLabTests.setOnClickListener(v -> openLabTestsActivity());
        cardHealthTips.setOnClickListener(v -> openHealthTipsActivity());
        cardChatbot.setOnClickListener(v -> openChatbotActivity());
        cardViewReports.setOnClickListener(v -> openViewReportsActivity());

        // Fetch doctors data from Firestore
        fetchDoctorsFromFirestore();

        // Initialize sign-out button and set the click listener
        btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(v -> signOut());
    }

    private void fetchDoctorsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("doctors")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    doctorList = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String name = documentSnapshot.getString("name");
                        String specialty = documentSnapshot.getString("specialty");

                        // Create a Doctor object without the image
                        Doctor doctor = new Doctor(name, specialty);
                        doctorList.add(doctor);
                    }

                    // Use the DoctorWithoutImageAdapter to display the doctor list
                    adapter = new DoctorWithoutImageAdapter(doctorList);
                    recyclerView.setAdapter(adapter);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(PatientHomeActivity.this, "Failed to load doctors: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void signOut() {
        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut();

        // Optionally, clear saved preferences (if any)
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();  // To clear all preferences
        editor.apply();

        // Navigate to PatientAuthActivity
        Intent intent = new Intent(PatientHomeActivity.this, PatientAuthActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void openAppointmentsActivity() {
        Intent intent = new Intent(PatientHomeActivity.this, AppointmentsActivity.class);
        startActivity(intent);
    }

    private void openBuyMedicinesActivity() {
        Intent intent = new Intent(PatientHomeActivity.this, BuyMedicinesActivity.class);
        startActivity(intent);
    }

    private void openLabTestsActivity() {
        Intent intent = new Intent(PatientHomeActivity.this, LabTestsActivity.class);
        startActivity(intent);
    }

    private void openHealthTipsActivity() {
        Intent intent = new Intent(PatientHomeActivity.this, HealthTipsActivity.class);
        startActivity(intent);
    }

    private void openChatbotActivity() {
        Intent intent = new Intent(PatientHomeActivity.this, ChatbotActivity.class);
        startActivity(intent);
    }

    private void openViewReportsActivity() {
        Intent intent = new Intent(PatientHomeActivity.this, ViewReportActivity.class);
        startActivity(intent);
    }
}
