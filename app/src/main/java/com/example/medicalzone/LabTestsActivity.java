package com.example.medicalzone;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabTestsActivity extends AppCompatActivity {

    private RecyclerView recyclerLabTests;
    private LabTestAdapter labTestAdapter;
    private List<LabTest> labTestsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_tests);

        recyclerLabTests = findViewById(R.id.recyclerLabTests);

        // Initialize RecyclerView
        recyclerLabTests.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list to store lab tests
        labTestsList = new ArrayList<>();

        // Initialize the adapter with an empty list for now
        labTestAdapter = new LabTestAdapter(this, labTestsList, labTest -> {
            // Show a dialog when a lab test is clicked
            showLabTestBookingDialog(labTest);
        });


        recyclerLabTests.setAdapter(labTestAdapter);

        // Fetch lab tests from Firestore
        fetchLabTests();

        // Add lab tests to Firestore if they don't already exist
        addLabTestsIfNeeded();
    }

    // Fetch lab tests from Firestore
    private void fetchLabTests() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("lab_tests")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(LabTestsActivity.this, "No lab tests available", Toast.LENGTH_SHORT).show();
                    } else {
                        labTestsList.clear();
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Assuming the document has fields like 'name', 'price', and 'description'
                            String name = documentSnapshot.getString("name");
                            double price = documentSnapshot.getDouble("price");
                            String description = documentSnapshot.getString("description");

                            // Create a new LabTest object and add it to the list
                            LabTest labTest = new LabTest(name, price, description);
                            labTestsList.add(labTest);
                        }

                        // Notify the adapter that the data has been updated
                        labTestAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(LabTestsActivity.this, "Error fetching lab tests: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Add predefined lab tests to Firestore if they don't exist
    private void addLabTestsIfNeeded() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("lab_tests")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        // Add lab tests to Firestore if the collection is empty
                        addLabTestsToFirestore();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(LabTestsActivity.this, "Error checking lab tests: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Method to add predefined lab tests to Firestore
    private void addLabTestsToFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Predefined lab tests data
        String[] labTestNames = {
                "Blood Test", "X-ray", "MRI", "Ultrasound", "CT Scan",
                "Electrocardiogram", "Echocardiogram", "Blood Sugar Test", "Thyroid Test", "Urine Test"
        };
        double[] labTestPrices = {50.00, 100.00, 200.00, 150.00, 250.00,
                120.00, 180.00, 30.00, 75.00, 20.00};
        String[] labTestDescriptions = {
                "A simple blood test to check your health.",
                "A medical imaging technique for diagnosing issues with bones and organs.",
                "A scan to look inside your body, especially for soft tissue.",
                "A test using high-frequency sound waves to examine internal organs.",
                "A type of medical imaging that uses X-rays and computer technology to create cross-sectional images.",
                "An ECG is used to measure the electrical activity of your heart.",
                "An imaging test to check the heart and its function.",
                "A test to check for blood sugar levels and diagnose diabetes.",
                "A test that checks thyroid function.",
                "A test to check the components in your urine, including sugar, protein, and other chemicals."
        };

        // Loop to add predefined lab tests to Firestore
        for (int i = 0; i < labTestNames.length; i++) {
            final String labTestName = labTestNames[i]; // Make the name effectively final
            final double labTestPrice = labTestPrices[i]; // Make the price effectively final
            final String labTestDescription = labTestDescriptions[i]; // Make the description effectively final

            // Create a new lab test object
            LabTest labTest = new LabTest(labTestName, labTestPrice, labTestDescription);

            // Add the lab test to Firestore
            db.collection("lab_tests")
                    .document("lab_test_" + (i + 1)) // Unique document ID
                    .set(labTest)
                    .addOnSuccessListener(aVoid -> {
                        // Optionally, log or notify success
                        Toast.makeText(LabTestsActivity.this, "Lab Test added: " + labTestName, Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(LabTestsActivity.this, "Error adding lab test: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    // Show booking dialog when a lab test is clicked
    private void showLabTestBookingDialog(LabTest labTest) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to book the " + labTest.getName() + " test for $" + labTest.getPrice() + "?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    // Add the lab test to cart and Firestore
                    addLabTestToCart(labTest);
                    Toast.makeText(this, "Lab test booked: " + labTest.getName(), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        builder.create().show();
    }

    // Add lab test to cart
    private void addLabTestToCart(LabTest labTest) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Prepare the data to add to the user's cart
        Map<String, Object> cartItem = new HashMap<>();
        cartItem.put("name", labTest.getName());
        cartItem.put("price", labTest.getPrice());
        cartItem.put("description", labTest.getDescription());
        cartItem.put("quantity", 1);  // Default quantity set to 1
        cartItem.put("type", "lab_test");  // Type field to distinguish lab tests

        // Add to Firestore under "userCart"
        db.collection("userCart")
                .add(cartItem)
                .addOnSuccessListener(documentReference -> {
                    // Log success
                    showToast(labTest.getName() + " added to cart.");
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    showToast("Error adding lab test to cart: " + e.getMessage());
                });
    }

    // Helper method to show toast messages
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
