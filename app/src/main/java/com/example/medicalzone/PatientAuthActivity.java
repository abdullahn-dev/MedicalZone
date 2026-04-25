package com.example.medicalzone;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class PatientAuthActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_auth);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.tvRegister);  // Ensure the Register button ID is correct in XML

        // Check if the user is already signed in
        if (mAuth.getCurrentUser() != null) {
            // User is already signed in, move to home activity
            goToHomeActivity();
        }

        btnLogin.setOnClickListener(v -> loginUser());

        // Add functionality to the "Register" button
        btnRegister.setOnClickListener(v -> goToRegisterPatientActivity());
    }

    private void loginUser() {
        // Check if there's internet connectivity
        if (!isConnectedToInternet()) {
            Toast.makeText(PatientAuthActivity.this, "No internet connection. Please connect to the internet to log in.", Toast.LENGTH_LONG).show();
            return;
        }

        // Get email and password from input fields
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate email and password
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(PatientAuthActivity.this, "Email or password cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Authenticate with Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Navigate to the home screen after successful login
                        goToHomeActivity();
                    } else {
                        // Handle login failure
                        Toast.makeText(PatientAuthActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle error
                    Toast.makeText(PatientAuthActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(PatientAuthActivity.this, PatientHomeActivity.class);
        startActivity(intent);
        finish();
    }

    // Navigate to the Register Patient Activity
    private void goToRegisterPatientActivity() {
        // Make sure this is the correct class for registering the patient
        Intent intent = new Intent(PatientAuthActivity.this, PatientRegisterActivity.class); // Replace with the correct Register Patient Activity class
        startActivity(intent);
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
