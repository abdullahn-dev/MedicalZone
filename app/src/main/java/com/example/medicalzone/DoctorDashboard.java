package com.example.medicalzone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorDashboard extends AppCompatActivity {

    // UI components for quick access cards
    private MaterialCardView cardAppointments, cardPatients, cardNotifications, cardProfile, cardViewReports;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        // Initialize quick access cards
        cardAppointments = findViewById(R.id.appointments_section);
        cardPatients = findViewById(R.id.patients_section);
        cardNotifications = findViewById(R.id.earnings);
        cardProfile = findViewById(R.id.profile_section);
        cardViewReports = findViewById(R.id.view_reports_section);

        // Set click listeners for the cards
        cardAppointments.setOnClickListener(v -> openAppointmentsActivity());
        cardPatients.setOnClickListener(v -> openPatientsActivity());
        cardNotifications.setOnClickListener(v -> openNotificationsActivity());
        cardProfile.setOnClickListener(v -> openProfileActivity());
        cardViewReports.setOnClickListener(v -> openViewReportsActivity());

        // Initialize sign-out button and set the click listener
        btnSignOut = findViewById(R.id.sign_out_button);
        btnSignOut.setOnClickListener(v -> signOut());
    }

    private void openAppointmentsActivity() {
        Intent intent = new Intent(DoctorDashboard.this, AppointmentsActivity.class);
        startActivity(intent);
    }

    private void openPatientsActivity() {
        Intent intent = new Intent(DoctorDashboard.this, PatientsActivity.class);
        startActivity(intent);
    }

    private void openNotificationsActivity() {
        Intent intent = new Intent(DoctorDashboard.this, NotificationsActivity.class);
        startActivity(intent);
    }

    private void openProfileActivity() {
        Intent intent = new Intent(DoctorDashboard.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void openViewReportsActivity() {
        Intent intent = new Intent(DoctorDashboard.this, ReportActivity.class);
        startActivity(intent);
    }

    private void signOut() {
        // Log to verify method is being called
        Log.d("DoctorDashboard", "Sign-out button clicked");

        // Optionally clear saved preferences (if needed)
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();  // Clear all data
        editor.apply();

        // Sign out from Firebase (if logged in with Firebase)
        FirebaseAuth.getInstance().signOut();

        // Navigate back to the login screen or appropriate activity
        Intent intent = new Intent(DoctorDashboard.this, DoctorAuthActivity.class); // Ensure DoctorAuthActivity is in the manifest
        startActivity(intent);
        finish(); // Close the current activity
    }
}
