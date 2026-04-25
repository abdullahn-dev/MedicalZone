package com.example.medicalzone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class ViewSalesActivity extends AppCompatActivity {

    private TextView txtTotalBill, txtProfit, txtActualSales;
    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sales);

        // Initialize TextViews and Button
        txtTotalBill = findViewById(R.id.txtTotalBill);
        txtProfit = findViewById(R.id.txtProfit);
        txtActualSales = findViewById(R.id.txtActualSales);
        btnRefresh = findViewById(R.id.btnRefresh);

        // Fetch sales summary when activity starts
        fetchSalesSummary();

        // Set OnClickListener for the Refresh button
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reload the data when button is clicked
                fetchSalesSummary();
            }
        });
    }

    // Method to fetch sales summary from Firestore
    private void fetchSalesSummary() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("sales")
                .document("summary")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Get values from Firestore
                        double totalBill = documentSnapshot.getDouble("totalBill");
                        double profit = documentSnapshot.getDouble("profit");
                        double actualSales = documentSnapshot.getDouble("actualSales");

                        // Update UI with the fetched data
                        txtTotalBill.setText("Total Sales: $" + String.format("%.2f", totalBill));
                        txtProfit.setText("Profit (20%): $" + String.format("%.2f", profit));
                        txtActualSales.setText("Actual Inventory Sales: $" + String.format("%.2f", actualSales));
                    } else {
                        // In case no data is available in Firestore
                        txtTotalBill.setText("Total Sales: $0.00");
                        txtProfit.setText("Profit (20%): $0.00");
                        txtActualSales.setText("Actual Inventory Sales: $0.00");
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle error fetching data
                    txtTotalBill.setText("Error fetching sales data.");
                    txtProfit.setText("");
                    txtActualSales.setText("");
                });
    }
}
