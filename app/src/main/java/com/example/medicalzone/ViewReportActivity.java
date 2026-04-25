package com.example.medicalzone;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewReportActivity extends AppCompatActivity {

    private Button btnViewReport;
    private String pdfUrl = "https://drive.google.com/uc?export=download&id=14ev3ckllUuvfJ9N635EKC14RD8KJsOXB"; // Your PDF URL here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        btnViewReport = findViewById(R.id.btnViewReport);

        btnViewReport.setOnClickListener(v -> openPdfWithExplicitIntent(pdfUrl));
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
}
