package com.example.medicalzone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 1;

    private EditText patientName, patientAge, diagnosis, medications, notes;
    private Button generatePdfButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        patientName = findViewById(R.id.patientName);
        patientAge = findViewById(R.id.patientAge);
        diagnosis = findViewById(R.id.diagnosis);
        medications = findViewById(R.id.medications);
        notes = findViewById(R.id.notes);
        generatePdfButton = findViewById(R.id.generatePdfButton);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }

        generatePdfButton.setOnClickListener(v -> generatePdf());
    }

    private void generatePdf() {
        String sanitizedPatientName = patientName.getText().toString().replaceAll("[^a-zA-Z0-9]", "_");
        String fileName = "PatientDetails_" + sanitizedPatientName + "_" + System.currentTimeMillis() + ".pdf";

        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, fileName);
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Paint paint = new Paint();
        int x = 10, y = 25;

        page.getCanvas().drawText("Patient Name: " + patientName.getText().toString(), x, y, paint);
        y += 20;
        page.getCanvas().drawText("Patient Age: " + patientAge.getText().toString(), x, y, paint);
        y += 20;
        page.getCanvas().drawText("Diagnosis: " + diagnosis.getText().toString(), x, y, paint);
        y += 20;
        page.getCanvas().drawText("Medications: " + medications.getText().toString(), x, y, paint);
        y += 20;
        page.getCanvas().drawText("Notes: " + notes.getText().toString(), x, y, paint);

        pdfDocument.finishPage(page);

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "PDF generated successfully!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, DoctorDashboard.class);
            intent.putExtra("pdfPath", file.getAbsolutePath());
            startActivity(intent);

        } catch (IOException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        pdfDocument.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Storage permission is required to save the PDF", Toast.LENGTH_LONG).show();
            }
        }
    }
}
