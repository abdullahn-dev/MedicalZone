package com.example.medicalzone;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView detailName;
    private TextView detailPrice;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailName = findViewById(R.id.detailName);
        detailPrice = findViewById(R.id.detailPrice);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Get data passed from the previous activity (LabTestsActivity)
        String name = getIntent().getStringExtra("test_name");
        double price = getIntent().getDoubleExtra("test_price", 0.0);

        // Set data to TextViews
        detailName.setText(name);
        detailPrice.setText("Price: $" + price);

        // Set click listener on checkout button
        checkoutButton.setOnClickListener(v -> {
            // Display a simple message on checkout
            Toast.makeText(DetailActivity.this, "Proceeding to Checkout...", Toast.LENGTH_SHORT).show();
        });
    }
}
