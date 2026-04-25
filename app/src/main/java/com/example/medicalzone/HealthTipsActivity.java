package com.example.medicalzone;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HealthTipsActivity extends AppCompatActivity {

    private RecyclerView recyclerHealthTips;
    private HealthTipAdapter healthTipAdapter;
    private List<HealthArticle> healthArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);

        recyclerHealthTips = findViewById(R.id.recyclerHealthTips);

        // Set up RecyclerView with a LinearLayoutManager
        recyclerHealthTips.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the health articles list
        healthArticles = getHealthArticles();

        // Set adapter for RecyclerView
        healthTipAdapter = new HealthTipAdapter(healthArticles);
        recyclerHealthTips.setAdapter(healthTipAdapter);

        // Set click listener for each item in RecyclerView
        healthTipAdapter.setOnItemClickListener(new HealthTipAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Get the selected article
                HealthArticle selectedArticle = healthArticles.get(position);

                // Open the selected PDF using the method below
                openPdfWithExplicitIntent(selectedArticle.getPdfUrl());
            }
        });
    }

    private void openPdfWithExplicitIntent(String pdfUrl) {
        // Create an Intent to open PDF with Adobe Acrobat Reader explicitly
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri pdfUri = Uri.parse(pdfUrl);
        intent.setDataAndType(pdfUri, "application/pdf");

        // Explicitly set Adobe Acrobat Reader as the target component
        ComponentName componentName = new ComponentName("com.adobe.reader", "com.adobe.reader.AdobeReader");
        intent.setComponent(componentName);

        // Check if Adobe Acrobat Reader is installed
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // If Adobe Acrobat Reader is not installed, fall back to any PDF viewer
            intent.setComponent(null); // Removes the specific app target
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                // If no PDF viewer is found, show a message to the user
                Toast.makeText(this, "No PDF viewer found. Please install Adobe Acrobat Reader.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<HealthArticle> getHealthArticles() {
        List<HealthArticle> articles = new ArrayList<>();
        articles.add(new HealthArticle(
                "Reduce stress",
                "https://drive.google.com/uc?export=download&id=1g4HtV8rkIUYcr5Hq88CqOisAmoDPhT6O",
                "Managing stress is essential for maintaining overall health, and relaxation techniques can help reduce anxiety."
        ));
        articles.add(new HealthArticle(
                "Get enough sleep",
                "https://drive.google.com/uc?export=download&id=1WnKTgRL2SOv7eF2CVhCtf2YoN8zd9u55",
                "Adequate sleep improves cognitive function, boosts immune health, and supports emotional well-being."
        ));
        articles.add(new HealthArticle(
                "Eat a balanced diet",
                "https://drive.google.com/uc?export=download&id=1dAein1pBSJ_Ju8DiBIVVqA4jNuY13zAk",
                "Eating a variety of fruits, vegetables, and proteins ensures that your body receives essential nutrients."
        ));
        articles.add(new HealthArticle(
                "Exercise regularly",
                "https://drive.google.com/uc?export=download&id=195rMHjc1baC3Wgs8Wfi1w9RYU2-9BNGd",
                "Regular exercise helps with weight management, reduces the risk of chronic diseases, and boosts mood."
        ));
        articles.add(new HealthArticle(
                "Drink plenty of water",
                "https://drive.google.com/uc?export=download&id=1dJ9sg7-1qM_NSS29SbwXAmasogZpHnCy",
                "Drinking enough water is essential for maintaining hydration, supporting digestion, and improving energy levels."
        ));

        return articles;
    }


}
