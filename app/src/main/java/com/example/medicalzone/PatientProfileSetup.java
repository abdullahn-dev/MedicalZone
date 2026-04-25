package com.example.medicalzone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class PatientProfileSetup extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imgProfilePicture;
    private EditText etName, etAge, etGender, etEmail, etPhone, etDisease;
    private Button btnSaveProfile;
    private Bitmap selectedProfilePicture;

    private PatientDBHelper patientDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        // Initialize Views
        imgProfilePicture = findViewById(R.id.imgProfilePicture);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etGender = findViewById(R.id.etGender);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etDisease = findViewById(R.id.etDisease);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        // Initialize DB Helper
        patientDBHelper = new PatientDBHelper(this);

        // Set up Profile Picture Click Listener
        imgProfilePicture.setOnClickListener(v -> openImagePicker());

        // Set up Save Button Click Listener
        btnSaveProfile.setOnClickListener(v -> saveProfile());
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                selectedProfilePicture = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imgProfilePicture.setImageBitmap(selectedProfilePicture);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveProfile() {
        // Get Input Data
        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String gender = etGender.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String disease = etDisease.getText().toString().trim();

        // Validate Input
        if (name.isEmpty() || age.isEmpty() || gender.isEmpty() || email.isEmpty() || phone.isEmpty() || disease.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedProfilePicture == null) {
            Toast.makeText(this, "Please upload a profile picture", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert Bitmap to Byte Array
        byte[] profilePictureBytes = DBHelperUtils.getBytesFromBitmap(selectedProfilePicture);

        // Save Data to Database
        boolean isInserted = patientDBHelper.insertPatient(name, age, gender, email, phone, disease, profilePictureBytes);
        if (isInserted) {
            Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to save profile", Toast.LENGTH_SHORT).show();
        }
    }
}
