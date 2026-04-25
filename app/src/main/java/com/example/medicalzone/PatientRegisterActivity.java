package com.example.medicalzone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;
import com.example.medicalzone.User;  // Correct import

public class PatientRegisterActivity extends AppCompatActivity {

    private EditText etEmail, etUsername, etPassword, etConfirmPassword;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(view -> {
            String email = etEmail.getText().toString();
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            // Validate inputs
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Inside PatientRegisterActivity
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Create a User object with both username and email
                            User userProfile = new User(username, email);  // This uses your custom User class

                            // Add user info to Firestore
                            db.collection("patients").document(user.getUid())
                                    .set(userProfile)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(PatientRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        // Redirect to login activity
                                        Intent intent = new Intent(PatientRegisterActivity.this, PatientAuthActivity.class);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(PatientRegisterActivity.this, "Error saving user data", Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            // Log the error message for debugging
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                            Toast.makeText(PatientRegisterActivity.this, "Registration Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            // Log the error for further analysis
                            Log.e("RegistrationError", errorMessage);
                        }
                    });


        });  // End of btnRegister.setOnClickListener

    }  // End of onCreate
}  // End of PatientRegisterActivity
