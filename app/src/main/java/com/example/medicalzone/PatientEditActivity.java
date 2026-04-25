package com.example.medicalzone;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class PatientEditActivity extends AppCompatActivity {

    private EditText editName, editEmail, editIllness;
    private Button saveButton;
    private FirebaseFirestore db;
    private String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        editName = findViewById(R.id.editTextName);
        editEmail = findViewById(R.id.editTextEmail);
        editIllness = findViewById(R.id.editTextIllness);
        saveButton = findViewById(R.id.saveButton);

        // Get the patient ID from the intent
        patientId = getIntent().getStringExtra("patientId");

        if (patientId != null) {
            // Fetch the patient's current details from Firestore
            fetchPatientDetails(patientId);
        } else {
            Toast.makeText(this, "Patient ID is missing", Toast.LENGTH_SHORT).show();
        }

        // Set the save button click listener
        saveButton.setOnClickListener(v -> savePatientDetails());
    }

    // Fetch patient details from Firestore
    private void fetchPatientDetails(String patientId) {
        db.collection("patients").document(patientId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Patientt patient = documentSnapshot.toObject(Patientt.class);
                        if (patient != null) {
                            // Fill the EditText fields with current patient details
                            editName.setText(patient.getName());
                            editEmail.setText(patient.getEmail());
                            editIllness.setText(patient.getIllness());
                        }
                    } else {
                        Toast.makeText(this, "Patient not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error fetching patient details", Toast.LENGTH_SHORT).show();
                });
    }

    // Save updated patient details in Firestore
    private void savePatientDetails() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String illness = editIllness.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(illness)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Patientt object with the updated details
        Patientt updatedPatient = new Patientt(name, email, illness);

        // Update patient in Firestore
        db.collection("patients").document(patientId)
                .set(updatedPatient)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Patient details updated", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error updating patient details", Toast.LENGTH_SHORT).show();
                });
    }
}
