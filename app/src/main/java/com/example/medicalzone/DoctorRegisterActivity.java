package com.example.medicalzone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorRegisterActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etUsername, etPassword, etConfirmPassword;
    private Spinner spinnerSpecialization;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        spinnerSpecialization = findViewById(R.id.spinnerSpecialization);
        btnRegister = findViewById(R.id.btnRegister);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Populate the Spinner with specializations from resources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.specializations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialization.setAdapter(adapter);

        // Set up register button click listener
        btnRegister.setOnClickListener(v -> {
            // Get input values
            String email = etEmail.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            String specialization = spinnerSpecialization.getSelectedItem().toString();

            // Validate input
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || specialization.isEmpty()) {
                Toast.makeText(DoctorRegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                Toast.makeText(DoctorRegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Register the doctor using Firebase Auth
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Create a doctr object with both username, email, and specialization
                            doctr doctorProfile = new doctr(username, email, specialization);

                            // Add doctor info to Firestore
                            db.collection("doctors").document(user.getUid())
                                    .set(doctorProfile)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(DoctorRegisterActivity.this, "Doctor registered successfully", Toast.LENGTH_SHORT).show();
                                        // Redirect to login activity
                                        Intent intent = new Intent(DoctorRegisterActivity.this, DoctorAuthActivity.class);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(DoctorRegisterActivity.this, "Error saving doctor data", Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(DoctorRegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
