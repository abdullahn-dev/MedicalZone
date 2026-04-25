package com.example.medicalzone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

public class UpdateMedicineActivity extends AppCompatActivity {

    private EditText edtPrice, edtQuantity;
    private Button btnSuccess;
    private TextView medicineNameTextView;
    private String documentId;  // To hold the document ID of the medicine to update
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_medicine);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        edtPrice = findViewById(R.id.edtPrice);
        edtQuantity = findViewById(R.id.edtQuantity);
        btnSuccess = findViewById(R.id.btnSuccess);
        medicineNameTextView = findViewById(R.id.medicineNameTextView);

        // Get the passed data from the Intent
        Intent intent = getIntent();
        String medicineName = intent.getStringExtra("medicine_name");
        String medicinePrice = intent.getStringExtra("medicine_price");
        String medicineQuantity = intent.getStringExtra("medicine_quantity");
        documentId = intent.getStringExtra("medicine_id"); // Pass the document ID

        // Set the values in the views
        if (medicineName != null) {
            medicineNameTextView.setText(medicineName);
        }
        if (medicinePrice != null) {
            edtPrice.setText(medicinePrice);
        }
        if (medicineQuantity != null) {
            edtQuantity.setText(medicineQuantity);
        }

        // Set up the button click listener
        btnSuccess.setOnClickListener(v -> {
            String updatedPrice = edtPrice.getText().toString();
            String updatedQuantity = edtQuantity.getText().toString();

            if (!updatedPrice.isEmpty() && !updatedQuantity.isEmpty()) {
                // Update medicine data in Firestore
                updateMedicineInFirestore(updatedPrice, updatedQuantity);
            } else {
                Toast.makeText(UpdateMedicineActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to update the medicine data in Firestore
    private void updateMedicineInFirestore(String updatedPrice, String updatedQuantity) {
        // Create a map of the fields to update
        DocumentReference medicineRef = db.collection("medicines").document(documentId);

        medicineRef.update(
                "price", updatedPrice,
                "quantity", updatedQuantity
        ).addOnSuccessListener(aVoid -> {
            Toast.makeText(UpdateMedicineActivity.this, "Medicine updated successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity and go back to the previous screen
        }).addOnFailureListener(e -> {
            Toast.makeText(UpdateMedicineActivity.this, "Error updating medicine: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}
