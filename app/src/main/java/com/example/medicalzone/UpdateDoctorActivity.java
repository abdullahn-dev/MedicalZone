package com.example.medicalzone;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateDoctorActivity extends AppCompatActivity {

    private EditText editTextName, editTextSpecialty;
    private Button updateButton;
    private String doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextSpecialty = findViewById(R.id.editTextSpecialty);
        updateButton = findViewById(R.id.updateButton);

        // Get the doctor ID passed through the intent
        doctorId = getIntent().getStringExtra("doctorId");

        // Fetch the current doctor's details from Firestore
        fetchDoctorDetails();

        // Set up the update button click listener
        updateButton.setOnClickListener(v -> updateDoctorDetails());
    }

    private void fetchDoctorDetails() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("doctors")
                .document(doctorId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Get the doctor's current details from Firestore
                        String name = documentSnapshot.getString("name");
                        String specialty = documentSnapshot.getString("specialty");

                        // Set the current details in the input fields
                        editTextName.setText(name);
                        editTextSpecialty.setText(specialty);
                    } else {
                        Toast.makeText(UpdateDoctorActivity.this, "Doctor not found!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(UpdateDoctorActivity.this, "Error fetching doctor details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void updateDoctorDetails() {
        // Get the updated name and specialty from the input fields
        String updatedName = editTextName.getText().toString().trim();
        String updatedSpecialty = editTextSpecialty.getText().toString().trim();

        if (TextUtils.isEmpty(updatedName) || TextUtils.isEmpty(updatedSpecialty)) {
            Toast.makeText(UpdateDoctorActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the doctor's details in Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("doctors")
                .document(doctorId)
                .update("name", updatedName, "specialty", updatedSpecialty)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(UpdateDoctorActivity.this, "Doctor details updated successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(UpdateDoctorActivity.this, "Error updating doctor details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
