package com.example.medicalzone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class AdminDashboardActivity extends AppCompatActivity {

    private MaterialCardView manageDoctorsCard, managePatientsCard, manageInventoryCard;
    private MaterialCardView viewSalesCard, viewAnalyticsCard, adminProfileCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize the cards
        manageDoctorsCard = findViewById(R.id.manage_doctors_card);
        managePatientsCard = findViewById(R.id.manage_patients_card);
        manageInventoryCard = findViewById(R.id.manage_inventory_card);
        viewSalesCard = findViewById(R.id.view_sales_card);
        viewAnalyticsCard = findViewById(R.id.view_analytics_card);
        adminProfileCard = findViewById(R.id.admin_profile_card);

        // Initialize the Sign Out button

        Button signOutButton = findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AdminFragmentActivity on sign-out
                Intent intent = new Intent(AdminDashboardActivity.this, AdminFragment.class);
                startActivity(intent);

                // Optionally, you can call finish() to close the current activity if you want to remove it from the back stack
                finish();
            }
        });

        Button profitButton = findViewById(R.id.profit_button);
        profitButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(AdminDashboardActivity.this, AppointmentProfitActivity.class);
                                                startActivity(intent);
                                            }
                                        });
        // Set onClickListeners for each card
        setCardClickListener(manageDoctorsCard, ManageDoctorsActivity.class);
        setCardClickListener(managePatientsCard, ManagePatientsActivity.class);
        setCardClickListener(manageInventoryCard, ManageInventoryActivity.class);
        setCardClickListener(viewSalesCard, ViewSalesActivity.class);
        setCardClickListener(viewAnalyticsCard, ViewAnalyticsActivity.class);
        setCardClickListener(adminProfileCard, AdminProfileActivity.class);
    }

    private void setCardClickListener(MaterialCardView cardView, final Class<?> activityClass) {
        if (cardView != null) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(AdminDashboardActivity.this, activityClass);
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.e("AdminDashboard", "Error starting activity", e);
                    }
                }
            });
        }
    }
}
