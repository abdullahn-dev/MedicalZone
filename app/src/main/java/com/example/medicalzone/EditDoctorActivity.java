package com.example.medicalzone;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class EditDoctorActivity extends AppCompatActivity {

    private EditText editName, editSpecialty;
    private Button saveButton;
    private FirebaseFirestore db;
    private String doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        editName = findViewById(R.id.editTextName);
        editSpecialty = findViewById(R.id.editTextSpecialty);
        saveButton = findViewById(R.id.saveButton);

        // Get the doctor ID from the intent
        doctorId = getIntent().getStringExtra("doctorId");

        if (doctorId != null) {
            // Fetch the doctor's current details from Firestore
            fetchDoctorDetails(doctorId);
        } else {
            Toast.makeText(this, "Doctor ID is missing", Toast.LENGTH_SHORT).show();
        }

        // Set the save button click listener
        saveButton.setOnClickListener(v -> saveDoctorDetails());
    }

    // Fetch doctor details from Firestore
    private void fetchDoctorDetails(String doctorId) {
        Log.d("EditDoctorActivity", "Fetching doctor details for ID: " + doctorId);

        db.collection("doctors").document(doctorId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Doctorr doctor = documentSnapshot.toObject(Doctorr.class);
                        if (doctor != null) {
                            Log.d("EditDoctorActivity", "Doctor found: " + doctor.getName());
                            // Fill the EditText fields with current doctor details
                            editName.setText(doctor.getName());
                            editSpecialty.setText(doctor.getSpecialty());
                        }
                    } else {
                        Log.e("EditDoctorActivity", "Doctor not found");
                        Toast.makeText(this, "Doctor not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("EditDoctorActivity", "Error fetching doctor details", e);
                    Toast.makeText(this, "Error fetching doctor details", Toast.LENGTH_SHORT).show();
                });
    }

    private void saveDoctorDetails() {
        String name = editName.getText().toString().trim();
        String specialty = editSpecialty.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(specialty)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Doctorr object with the updated details and doctorId
        Doctorr updatedDoctor = new Doctorr(doctorId, name, specialty); // Corrected constructor usage

        // Update doctor in Firestore
        db.collection("doctors").document(doctorId)
                .set(updatedDoctor)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Doctor details updated", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error updating doctor details", Toast.LENGTH_SHORT).show();
                });
    }

}
