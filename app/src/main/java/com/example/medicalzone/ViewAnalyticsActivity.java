package com.example.medicalzone;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewAnalyticsActivity extends AppCompatActivity {

    private TextView totalSalesText, profitText, actualSalesText;
    private CustomBarChartView salesBarChart;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_analytics);

        // Initialize views
        totalSalesText = findViewById(R.id.totalSalesText);
        profitText = findViewById(R.id.profitText);
        actualSalesText = findViewById(R.id.actualSalesText);
        salesBarChart = findViewById(R.id.salesBarChart);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Fetch sales data from Firestore
        fetchSalesData();
    }

    private void fetchSalesData() {
        db.collection("sales")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        double totalBill = 0.0, profit = 0.0, actualSales = 0.0;
                        ArrayList<Float> salesData = new ArrayList<>();
                        ArrayList<Float> profitData = new ArrayList<>();

                        // Loop through each document in the sales collection
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            double saleActualSales = documentSnapshot.getDouble("actualSales");
                            double saleProfit = documentSnapshot.getDouble("profit");
                            double saleTotalBill = documentSnapshot.getDouble("totalBill");

                            // Aggregating totals
                            actualSales += saleActualSales;
                            profit += saleProfit;
                            totalBill += saleTotalBill;

                            // Add data to the respective arrays
                            salesData.add((float) saleTotalBill);
                            profitData.add((float) saleProfit);
                        }

                        // Update UI with the aggregated data
                        totalSalesText.setText("Total Sales: $" + String.format("%.2f", totalBill));
                        profitText.setText("Profit: $" + String.format("%.2f", profit));
                        actualSalesText.setText("Actual Sales: $" + String.format("%.2f", actualSales));

                        // Set data for the CustomBarChartView
                        salesBarChart.setData(salesData, profitData);
                    } else {
                        // Handle error
                        Log.e("FirestoreData", "Error fetching data: ", task.getException());
                        Toast.makeText(ViewAnalyticsActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
