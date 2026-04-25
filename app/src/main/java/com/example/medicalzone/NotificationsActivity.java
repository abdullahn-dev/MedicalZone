package com.example.medicalzone;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class NotificationsActivity extends AppCompatActivity {

    // Views for displaying the data
    TextView totalPatientsTextView, adminProfitTextView, yourEarningsTextView, totalEarningsTextView;

    // Firebase Firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Fixed values for earnings calculation
    private static final int APPOINTMENT_FEE_USD = 1000; // $1000 per appointment (stored in USD)
    private static final double ADMIN_PERCENTAGE = 0.1;  // 10% for Admin
    private static final double YOUR_PERCENTAGE = 0.9;  // 90% for Doctor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Initialize TextViews
        totalPatientsTextView = findViewById(R.id.total_patients_text_view);
        adminProfitTextView = findViewById(R.id.admin_profit_text_view);
        yourEarningsTextView = findViewById(R.id.your_earnings_text_view);
        totalEarningsTextView = findViewById(R.id.total_earnings_text_view);

        // Set up the back button click listener
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back to the previous one
                onBackPressed();
            }
        });

        // Fetch the appointments data from Firebase
        fetchAppointments();
    }

    private void fetchAppointments() {
        db.collection("appointments") // Assuming your collection is named "appointments"
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot documents = task.getResult();

                        // Get total number of appointments
                        int totalAppointments = documents.size();

                        // Calculate the earnings in USD
                        double totalAdminProfitInUSD = totalAppointments * APPOINTMENT_FEE_USD * ADMIN_PERCENTAGE;
                        double totalYourEarningsInUSD = totalAppointments * APPOINTMENT_FEE_USD * YOUR_PERCENTAGE;
                        double totalEarningsInUSD = totalAdminProfitInUSD + totalYourEarningsInUSD;

                        // Update the UI with the calculated values in USD
                        totalPatientsTextView.setText("Total Patients Served: " + totalAppointments);
                        adminProfitTextView.setText("Admin Profit: $" + String.format("%.2f", totalAdminProfitInUSD));
                        yourEarningsTextView.setText("Your Earnings: $" + String.format("%.2f", totalYourEarningsInUSD));
                        totalEarningsTextView.setText("Total Earnings: $" + String.format("%.2f", totalEarningsInUSD));
                    } else {
                        // Handle error in fetching data
                        totalPatientsTextView.setText("Error fetching data");
                    }
                });
    }
}
