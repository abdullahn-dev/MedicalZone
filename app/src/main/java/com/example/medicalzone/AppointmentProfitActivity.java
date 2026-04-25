package com.example.medicalzone;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AppointmentProfitActivity extends AppCompatActivity {

    private TextView totalAppointmentsText, adminProfitText, doctorProfitText, totalProfitText;

    // Firestore instance
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_profit);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        totalAppointmentsText = findViewById(R.id.total_appointments);
        adminProfitText = findViewById(R.id.admin_profit);
        doctorProfitText = findViewById(R.id.doctor_profit);
        totalProfitText = findViewById(R.id.total_profit);

        // Back button functionality
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back button click
                onBackPressed(); // This will take the user to the previous activity
            }
        });

        // Fetch appointment data from Firestore
        fetchAppointmentsData();
    }

    private void fetchAppointmentsData() {
        // Get a reference to the "appointments" collection
        db.collection("appointments")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // On successful fetch
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            int totalAppointments = querySnapshot.size(); // Total number of appointments
                            double pricePerAppointment = 1000; // Example price per appointment

                            // Admin gets 10% of the price
                            double adminProfit = totalAppointments * pricePerAppointment * 0.10;
                            // Doctor gets 90% of the price
                            double doctorConsultationFee = totalAppointments * pricePerAppointment * 0.90;

                            // Update UI
                            totalAppointmentsText.setText("Total Appointments: " + totalAppointments);
                            adminProfitText.setText("Admin Profit: " + adminProfit);
                            doctorProfitText.setText("Doctor's Consultation Fee: " + doctorConsultationFee);

                            // Only admin profit is shown in total profit
                            totalProfitText.setText("Total Profit (Admin): " + adminProfit);
                        }
                    } else {
                        // Handle failure
                        totalAppointmentsText.setText("Failed to load data.");
                    }
                });
    }
}
