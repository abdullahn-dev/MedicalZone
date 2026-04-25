package com.example.medicalzone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddPatientActivity extends AppCompatActivity {

    private EditText editName, editEmail, editIllness;
    private Button addPatientButton, bulkAddButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        editName = findViewById(R.id.editTextName);
        editEmail = findViewById(R.id.editTextEmail);
        editIllness = findViewById(R.id.editTextIllness);
        addPatientButton = findViewById(R.id.addPatientButton);
        bulkAddButton = findViewById(R.id.bulkAddButton);

        // Add single patient to Firestore
        addPatientButton.setOnClickListener(v -> addPatient());

        // Add bulk patients to Firestore
        bulkAddButton.setOnClickListener(v -> bulkAddPatients());
    }

    // Add a single patient to Firestore
    private void addPatient() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String illness = editIllness.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || illness.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create patient object
        Patientt newPatient = new Patientt(name, email, illness);

        // Save to Firestore
        db.collection("patients").add(newPatient)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Patient added successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close activity
                })
                .addOnFailureListener(e -> {
                    Log.e("AddPatient", "Error adding patient: ", e);
                    Toast.makeText(this, "Error adding patient", Toast.LENGTH_SHORT).show();
                });
    }

    // Bulk add patients to Firestore (example with dummy data)
    private void bulkAddPatients() {
        List<Patientt> bulkPatients = new ArrayList<>();

        // Create 10 sample patients for bulk upload
        bulkPatients.add(new Patientt("John Doe", "john@example.com", "Flu"));
        bulkPatients.add(new Patientt("Jane Smith", "jane@example.com", "Cold"));
        bulkPatients.add(new Patientt("Mike Johnson", "mike@example.com", "Headache"));
        bulkPatients.add(new Patientt("Alice Brown", "alice@example.com", "Fever"));
        bulkPatients.add(new Patientt("Bob Green", "bob@example.com", "Cough"));
        bulkPatients.add(new Patientt("Charlie Black", "charlie@example.com", "Asthma"));
        bulkPatients.add(new Patientt("David White", "david@example.com", "Migraine"));
        bulkPatients.add(new Patientt("Emma Blue", "emma@example.com", "Nausea"));
        bulkPatients.add(new Patientt("Frank Grey", "frank@example.com", "Back Pain"));
        bulkPatients.add(new Patientt("Grace Yellow", "grace@example.com", "Stomach Ache"));

        // Add each patient to Firestore
        for (Patientt patient : bulkPatients) {
            db.collection("patients").add(patient)
                    .addOnSuccessListener(documentReference -> {
                        Log.d("BulkAddPatients", "Patient added: " + patient.getName());
                    })
                    .addOnFailureListener(e -> {
                        Log.e("BulkAddPatients", "Error adding patient: ", e);
                    });
        }

        Toast.makeText(this, "Bulk patients added", Toast.LENGTH_SHORT).show();
    }
}
