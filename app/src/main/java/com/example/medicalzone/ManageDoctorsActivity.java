package com.example.medicalzone;

import android.content.Intent;
import android.os.Bundle;
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

public class ManageDoctorsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DoctorrAdapter doctorrAdapter;
    private List<Doctorr> doctorList;
    private Button addDoctorButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctors);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewDoctorrs);
        addDoctorButton = findViewById(R.id.add_doctor_button);

        // Set up RecyclerView with Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorList = new ArrayList<>();
        doctorrAdapter = new DoctorrAdapter(doctorList, new DoctorrAdapter.OnItemClickListener() {
            @Override
            public void onUpdateClick(Doctorr doctor) {
                // Handle updating the doctor (open EditDoctorActivity)
                Intent intent = new Intent(ManageDoctorsActivity.this, EditDoctorActivity.class);
                intent.putExtra("doctorId", doctor.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Doctorr doctor) {
                // Handle deleting the doctor
                db.collection("doctors").document(doctor.getId()).delete()
                        .addOnSuccessListener(aVoid -> {
                            doctorList.remove(doctor);
                            doctorrAdapter.notifyDataSetChanged();
                            Toast.makeText(ManageDoctorsActivity.this, "Doctor deleted", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(ManageDoctorsActivity.this, "Error deleting doctor", Toast.LENGTH_SHORT).show();
                        });
            }
        });
        recyclerView.setAdapter(doctorrAdapter);

        // Load doctors from Firestore
        loadDoctorsFromFirestore();

        // Add doctor button click listener
        addDoctorButton.setOnClickListener(v -> {
            // Start AddDoctorActivity to add a new doctor
            Intent intent = new Intent(ManageDoctorsActivity.this, AddDoctorActivity.class);
            startActivity(intent);
        });
    }

    private void loadDoctorsFromFirestore() {
        db.collection("doctors")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Loop through all documents and add doctors to the list
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            String id = documentSnapshot.getId();
                            String name = documentSnapshot.getString("name");
                            String specialty = documentSnapshot.getString("specialty");

                            if (name != null && specialty != null) {
                                // Create a new doctor object
                                Doctorr doctor = new Doctorr(id, name, specialty);
                                // Add the doctor to the list
                                doctorList.add(doctor);
                            }
                        }
                        // Notify the adapter that data has been loaded
                        doctorrAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "No doctors available", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error loading doctors", Toast.LENGTH_SHORT).show();
                });
    }
}
