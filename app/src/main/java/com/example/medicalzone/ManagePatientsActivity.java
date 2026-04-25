package com.example.medicalzone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ManagePatientsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PatienttAdapter patienttAdapter;
    private List<Patientt> patientList;
    private Button addPatientButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_patients);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewPatients);
        addPatientButton = findViewById(R.id.add_patient_button);

        // Set up RecyclerView with Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        patientList = new ArrayList<>();
        patienttAdapter = new PatienttAdapter(ManagePatientsActivity.this, patientList, new PatienttAdapter.OnItemClickListener() {
            @Override
            public void onUpdateClick(Patientt patient) {
                // Handle update logic here
                Log.d("ManagePatientsActivity", "Update clicked for patient: " + patient.getName());
                updatePatient(patient);
            }

            @Override
            public void onDeleteClick(Patientt patient) {
                // Handle delete logic here
                Log.d("ManagePatientsActivity", "Delete clicked for patient: " + patient.getName());
                deletePatient(patient);
            }
        });

        recyclerView.setAdapter(patienttAdapter);

        // Fetch patients from Firestore
        fetchPatients();

        // Add patient button click listener
        addPatientButton.setOnClickListener(v -> {
            Intent intent = new Intent(ManagePatientsActivity.this, AddPatientActivity.class);
            startActivity(intent);
        });
    }

    // Fetch patients from Firestore
    private void fetchPatients() {
        db.collection("patients").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot snapshot = task.getResult();
                        if (snapshot != null) {
                            patientList.clear();
                            for (DocumentSnapshot document : snapshot) {
                                Patientt patient = document.toObject(Patientt.class);
                                if (patient != null) {
                                    patient.setId(document.getId()); // Ensure the patient has an ID
                                    patientList.add(patient);
                                }
                            }
                            patienttAdapter.notifyDataSetChanged(); // Notify the adapter that data has changed
                        }
                    } else {
                        Toast.makeText(ManagePatientsActivity.this, "Error fetching patients", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ManagePatientsActivity.this, "Error fetching patients: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Update patient details in Firestore
    private void updatePatient(Patientt patient) {
        String patientId = patient.getId();
        if (patientId != null) {
            Log.d("UpdatePatient", "Updating patient with ID: " + patientId);
            Intent intent = new Intent(ManagePatientsActivity.this, PatientEditActivity.class);
            intent.putExtra("patientId", patientId); // pass the patient's ID
            startActivity(intent);
        } else {
            Log.e("UpdatePatient", "Patient ID is null");
        }
    }

    // Delete patient from Firestore
    private void deletePatient(Patientt patient) {
        String patientId = patient.getId();
        if (patientId != null) {
            Log.d("DeletePatient", "Deleting patient with ID: " + patientId);
            db.collection("patients").document(patientId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Log.d("DeletePatient", "Patient deleted successfully");
                        Toast.makeText(ManagePatientsActivity.this, "Patient deleted successfully", Toast.LENGTH_SHORT).show();
                        fetchPatients();  // Refresh the list after deleting the patient
                    })
                    .addOnFailureListener(e -> {
                        Log.e("DeletePatient", "Error deleting patient: ", e);
                        Toast.makeText(ManagePatientsActivity.this, "Error deleting patient: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Log.e("DeletePatient", "Patient ID is null");
        }
    }
}
