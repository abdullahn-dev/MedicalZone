package com.example.medicalzone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorAuthActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_auth);

        // Initialize the views
        usernameEditText = findViewById(R.id.etUsername);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btnLogin);
        registerButton = findViewById(R.id.tvRegister);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Check if the doctor is already signed in
        if (mAuth.getCurrentUser() != null) {
            // Doctor is already signed in, move to the dashboard
            goToDoctorDashboard();
        }

        // Set the OnClickListener for the login button
        loginButton.setOnClickListener(v -> loginDoctor());

        // Set the OnClickListener for the register button
        registerButton.setOnClickListener(v -> goToDoctorRegisterActivity());
    }

    private void loginDoctor() {
        // Get email and password from the input fields
        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate inputs
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(DoctorAuthActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Authenticate the doctor using Firebase Auth
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Login successful, redirect to doctor dashboard
                        Toast.makeText(DoctorAuthActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        goToDoctorDashboard();
                    } else {
                        Toast.makeText(DoctorAuthActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DoctorAuthActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    // Navigate to Doctor Dashboard after successful login
    private void goToDoctorDashboard() {
        Intent intent = new Intent(DoctorAuthActivity.this, DoctorDashboard.class); // Replace with your actual Doctor Dashboard activity
        startActivity(intent);
        finish(); // Close the login activity
    }

    // Navigate to the Doctor Registration Activity
    private void goToDoctorRegisterActivity() {
        Intent intent = new Intent(DoctorAuthActivity.this, DoctorRegisterActivity.class); // Replace with your actual Registration activity
        startActivity(intent);
    }
}
