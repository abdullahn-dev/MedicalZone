package com.example.medicalzone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ManageInventoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MedicineItemAdapter medicineAdapter;
    private List<Medicinee> medicineList;
    private FirebaseFirestore db;
    private Button addInventoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_inventory);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerViewMedicines);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        medicineList = new ArrayList<>();
        medicineAdapter = new MedicineItemAdapter(medicineList, this);
        recyclerView.setAdapter(medicineAdapter);

        // Initialize Add Inventory Button
        addInventoryButton = findViewById(R.id.add_inventory_button);

        // Load medicines from Firestore
        loadMedicinesFromFirestore();

        // Set OnClickListener for Add Inventory Button
        addInventoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(ManageInventoryActivity.this, AddMedicineActivity.class);
            startActivity(intent);
        });
    }

    private void loadMedicinesFromFirestore() {
        db.collection("medicines")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(ManageInventoryActivity.this, "No medicines available", Toast.LENGTH_SHORT).show();
                    }
                    medicineList.clear();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String name = documentSnapshot.getString("name");
                        String price = documentSnapshot.getString("price");
                        String quantity = documentSnapshot.getString("quantity");
                        String expiryDate = documentSnapshot.getString("expiryDate");
                        String manufacturer = documentSnapshot.getString("manufacturer");
                        String description = documentSnapshot.getString("description");
                        String documentId = documentSnapshot.getId(); // Get document ID

                        Medicinee medicine = new Medicinee(name, price, quantity, expiryDate, manufacturer, description, documentId);
                        medicineList.add(medicine);
                    }
                    medicineAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ManageInventoryActivity.this, "Error fetching medicines: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
