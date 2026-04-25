package com.example.medicalzone;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyMedicinesActivity extends AppCompatActivity {

    private RecyclerView recyclerMedicines;
    private EditText searchMedicines;
    private MedicineAdapter adapter;
    private List<Medicine> medicineList;
    private List<Medicine> medicineListFull;  // List to store all medicines for filtering

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicines);

        recyclerMedicines = findViewById(R.id.recyclerMedicines);
        searchMedicines = findViewById(R.id.searchMedicines);

        // Initialize RecyclerView and Medicine list
        medicineList = new ArrayList<>();
        medicineListFull = new ArrayList<>();  // Create a full list for filtering
        adapter = new MedicineAdapter(medicineList, this); // Pass context to adapter
        recyclerMedicines.setLayoutManager(new LinearLayoutManager(this));
        recyclerMedicines.setAdapter(adapter);

        // Fetch medicines from Firestore
        fetchMedicines();

        // Add search functionality to filter medicines
        searchMedicines.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Trigger filter when text changes
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void fetchMedicines() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("medicines")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(BuyMedicinesActivity.this, "No medicines available", Toast.LENGTH_SHORT).show();
                    }
                    medicineList.clear();
                    medicineListFull.clear(); // Clear the full list too

                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String name = documentSnapshot.getString("name");
                        String price = documentSnapshot.getString("price");
                        String quantity = documentSnapshot.getString("quantity");
                        String expiryDate = documentSnapshot.getString("expiryDate");
                        String manufacturer = documentSnapshot.getString("manufacturer");
                        String description = documentSnapshot.getString("description");

                        // Create a new Medicine object
                        Medicine medicine = new Medicine(name, quantity, price, expiryDate, manufacturer, description);
                        medicineList.add(medicine);
                        medicineListFull.add(medicine);  // Add to the full list for filtering
                    }

                    // Notify the adapter to update the UI with the new list
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(BuyMedicinesActivity.this, "Error fetching medicines: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Show the dialog to select quantity and confirm purchase
    public void showQuantityDialog(Medicine medicine) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("If You want to buy, Please Enter Quantity");

        // Create an EditText for quantity input
        final EditText quantityEditText = new EditText(this);
        quantityEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(quantityEditText);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String quantityString = quantityEditText.getText().toString().trim();

            if (quantityString.isEmpty()) {
                Toast.makeText(BuyMedicinesActivity.this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(quantityString);
            double price = Double.parseDouble(medicine.getPrice().replace("$", "").trim());
            double totalBill = price * quantity;

            // Show confirmation dialog with the total bill
            showConfirmationDialog(medicine, quantity, totalBill);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Show confirmation dialog to confirm adding to cart
    private void showConfirmationDialog(Medicine medicine, int quantity, double totalBill) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Purchase");

        // Show the total price and quantity in the dialog
        String message = "Do you want to buy " + quantity + " of " + medicine.getName() + " for a total of $" + totalBill;
        builder.setMessage(message);

        builder.setPositiveButton("Yes", (dialog, which) -> {
            // Add medicine to cart (For simplicity, we just show a toast here)
            addMedicineToCart(medicine, quantity, totalBill);
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Method to add the medicine to cart
    private void addMedicineToCart(Medicine medicine, int quantity, double totalBill) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Prepare the data to add to the user's cart
        Map<String, Object> cartItem = new HashMap<>();
        cartItem.put("name", medicine.getName());
        cartItem.put("price", totalBill); // Store the total bill here
        cartItem.put("quantity", quantity);
        cartItem.put("type", "medicine");  // Type field to distinguish medicines

        // Add to Firestore under "userCart"
        db.collection("userCart")
                .add(cartItem)
                .addOnSuccessListener(documentReference -> {
                    // Log success
                    Log.d("Medicine", "Medicine added to cart: " + medicine.getName());
                    showToast(medicine.getName() + " added to cart.");
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Log.e("Medicine", "Error adding medicine to cart: " + e.getMessage());
                    showToast("Error adding medicine to cart: " + e.getMessage());
                });
    }

    // Helper method to show toast messages
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
