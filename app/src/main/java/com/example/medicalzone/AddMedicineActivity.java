package com.example.medicalzone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.Arrays;
import java.util.List;

public class AddMedicineActivity extends AppCompatActivity {

    private EditText nameEditText, quantityEditText, priceEditText, expiryDateEditText, manufacturerEditText, descriptionEditText;
    private FirebaseFirestore db;
    private Button addSingleMedicineButton, addBulkMedicinesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine); // Your layout file

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views from XML
        nameEditText = findViewById(R.id.name_edit_text);
        quantityEditText = findViewById(R.id.quantity_edit_text);
        priceEditText = findViewById(R.id.price_edit_text);
        expiryDateEditText = findViewById(R.id.expiry_date_edit_text);
        manufacturerEditText = findViewById(R.id.manufacturer_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);

        // Initialize buttons
        addSingleMedicineButton = findViewById(R.id.add_single_medicine_button);
        addBulkMedicinesButton = findViewById(R.id.add_bulk_medicines_button);

        // Set onClick listeners for the buttons
        addSingleMedicineButton.setOnClickListener(v -> addSingleMedicine());
        addBulkMedicinesButton.setOnClickListener(v -> addBulkMedicines());
    }

    // Method to add a single medicine manually
    private void addSingleMedicine() {
        String name = nameEditText.getText().toString().trim();
        String quantity = quantityEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        String expiryDate = expiryDateEditText.getText().toString().trim();
        String manufacturer = manufacturerEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        // Log to check values
        Log.d("AddMedicine", "Adding medicine with name: " + name + ", price: " + price + ", quantity: " + quantity);

        if (name.isEmpty() || quantity.isEmpty() || price.isEmpty() || expiryDate.isEmpty() || manufacturer.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Create a new medicine object with the data
            Medicine newMedicine = new Medicine(name, quantity, price, expiryDate, manufacturer, description);

            // Add the medicine to Firestore
            db.collection("medicines")
                    .add(newMedicine)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Medicine added successfully!", Toast.LENGTH_SHORT).show();
                        finish(); // Close this activity and return to the previous screen
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error adding medicine: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    // Method to add bulk medicines
    private void addBulkMedicines() {
        // List of medicines to be added in bulk
        List<Medicine> medicinesList = Arrays.asList(
                new Medicine("Paracetamol", "100", "50", "2025-05-01", "Pharma Inc.", "Pain reliever"),
                new Medicine("Aspirin", "150", "60", "2025-08-01", "Health Co.", "Anti-inflammatory"),
                new Medicine("Ibuprofen", "200", "40", "2026-02-01", "MedCare", "Fever reducer"),
                new Medicine("Amoxicillin", "300", "80", "2025-12-01", "MediPlus", "Antibiotic"),
                new Medicine("Ciprofloxacin", "120", "120", "2026-04-01", "MedTech", "Antibiotic")
        );

        // Create a batch for bulk write
        WriteBatch batch = db.batch();

        // Add each medicine to the batch
        for (Medicine medicine : medicinesList) {
            // Here, set() is used to define data in a specific document
            // Firestore will create a new document with a generated ID for each medicine
            batch.set(db.collection("medicines").document(), medicine);  // Use .document() to create new document ID for each medicine
        }

        // Commit the batch
        batch.commit()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AddMedicineActivity.this, "Bulk medicines added successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddMedicineActivity.this, "Error adding bulk medicines: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
