package com.example.medicalzone;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import java.util.Arrays;
import java.util.List;

public class AddDoctorActivity extends AppCompatActivity {

    private EditText nameEditText, specialtyEditText;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);  // Your layout file

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views from XML
        nameEditText = findViewById(R.id.name_edit_text);
        specialtyEditText = findViewById(R.id.specialty_edit_text);
    }

    // Method to add a single doctor manually
    public void addDoctor(View view) {
        String name = nameEditText.getText().toString().trim();
        String specialty = specialtyEditText.getText().toString().trim();

        // Check if fields are empty
        if (name.isEmpty() || specialty.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Create a new doctor object with the name and specialty
            Doctor newDoctor = new Doctor(name, specialty);

            // Add the doctor to Firestore
            db.collection("doctors")
                    .add(newDoctor)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Doctor added successfully!", Toast.LENGTH_SHORT).show();
                        finish(); // Close this activity and return to the previous screen
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error adding doctor: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    // Method to add bulk data (multiple doctors)
    public void addBulkDoctors(View view) {
        // List of doctors to be added in bulk
        List<Doctor> doctorsList = Arrays.asList(
                new Doctor("Dr. John Doe", "Cardiology"),
                new Doctor("Dr. Jane Smith", "Neurology"),
                new Doctor("Dr. Emma Brown", "Orthopedics"),
                new Doctor("Dr. James Johnson", "Dermatology"),
                new Doctor("Dr. Michael Davis", "Pediatrics"),
                new Doctor("Dr. Sarah Wilson", "Gynecology"),
                new Doctor("Dr. David Moore", "Psychiatry"),
                new Doctor("Dr. Maria Garcia", "Ophthalmology"),
                new Doctor("Dr. William Taylor", "Radiology"),
                new Doctor("Dr. Linda Anderson", "Pathology")
               );

        // Create a batch for bulk write
        WriteBatch batch = db.batch();

        // Add each doctor to the batch
        for (Doctor doctor : doctorsList) {
            // Here, set() is used to define data in a specific document
            // Firestore will create a new document with a generated ID
            batch.set(db.collection("doctors").document(), doctor);  // Use .document() to create new document ID for each doctor
        }

        // Commit the batch
        batch.commit()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AddDoctorActivity.this, "Bulk data added successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddDoctorActivity.this, "Error adding bulk data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}
