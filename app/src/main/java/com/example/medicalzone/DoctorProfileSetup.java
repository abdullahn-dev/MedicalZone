package com.example.medicalzone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class DoctorProfileSetup extends AppCompatActivity {

    private TextInputEditText etDoctorName;
    private TextInputEditText etSpecialization;
    private TextInputEditText etExperience;
    private Button btnSaveProfile;
    private ImageView ivProfileImage;
    private DoctorDBHelper doctorDBHelper;

    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    ivProfileImage.setImageURI(selectedImageUri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        // Initialize views
        etDoctorName = findViewById(R.id.etDoctorName);
        etSpecialization = findViewById(R.id.etSpecialization);
        etExperience = findViewById(R.id.etExperience);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        ivProfileImage = findViewById(R.id.ivProfileImage);

        // Initialize the database helper
        doctorDBHelper = new DoctorDBHelper(this);

        // Set image picker action
        ivProfileImage.setOnClickListener(v -> openImagePicker());

        // Set the save button action
        btnSaveProfile.setOnClickListener(v -> saveDoctorProfile());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void saveDoctorProfile() {
        String name = etDoctorName.getText().toString().trim();
        String specialization = etSpecialization.getText().toString().trim();
        String experience = etExperience.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(specialization) || TextUtils.isEmpty(experience)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Please upload a profile image", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int yearsOfExperience = Integer.parseInt(experience);

            // Insert data into the database
            boolean isInserted = doctorDBHelper.insertOrUpdateProfile(
                    name,
                    specialization,
                    yearsOfExperience,
                    selectedImageUri.toString()
            );

            if (isInserted) {
                Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to save profile", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid years of experience", Toast.LENGTH_SHORT).show();
        }
    }
}
