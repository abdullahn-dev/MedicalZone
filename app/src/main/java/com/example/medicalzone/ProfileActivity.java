package com.example.medicalzone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ViewSwitcher viewSwitcher;
    private SharedPreferences sharedPreferences;

    // Setup Profile UI elements
    private EditText editName, editEmail;
    private ImageView setupProfileImage;
    private Button saveProfileButton, uploadProfileButton;

    // View Profile UI elements
    private ImageView viewProfileImage;
    private TextView viewName, viewEmail;
    private Button editProfileButton;

    private Uri imageUri; // To store selected profile image URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize UI elements
        viewSwitcher = findViewById(R.id.profile_view_switcher);

        // Setup Profile
        setupProfileImage = findViewById(R.id.setup_profile_image);
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);

        saveProfileButton = findViewById(R.id.save_profile_button);
        uploadProfileButton = findViewById(R.id.upload_profile_button);

        // View Profile
        viewProfileImage = findViewById(R.id.view_profile_image);
        viewName = findViewById(R.id.view_name);
        viewEmail = findViewById(R.id.view_email);
        editProfileButton = findViewById(R.id.edit_profile_button);

        sharedPreferences = getSharedPreferences("admin_profile", MODE_PRIVATE);

        // Check if profile exists
        if (sharedPreferences.contains("name")) {
            displayProfile();
        } else {
            viewSwitcher.setDisplayedChild(0); // Show Setup Profile
        }

        // Upload Profile Picture
        uploadProfileButton.setOnClickListener(v -> openImagePicker());

        // Save Profile Button Logic
        saveProfileButton.setOnClickListener(v -> saveProfile());

        // Edit Profile Button Logic
        editProfileButton.setOnClickListener(v -> viewSwitcher.setDisplayedChild(0));
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            setupProfileImage.setImageURI(imageUri); // Display the selected image
        }
    }

    private void saveProfile() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", editName.getText().toString());
        editor.putString("email", editEmail.getText().toString());

        // Save the profile image URI as a string
        if (imageUri != null) {
            editor.putString("image_uri", imageUri.toString());
        }
        editor.apply();

        displayProfile();
    }

    private void displayProfile() {
        viewSwitcher.setDisplayedChild(1); // Switch to View Profile

        // Load data from SharedPreferences
        viewName.setText("Name: " + sharedPreferences.getString("name", ""));
        viewEmail.setText("Email: " + sharedPreferences.getString("email", ""));

        // Load the profile image if it exists
        String imageUriString = sharedPreferences.getString("image_uri", null);
        if (imageUriString != null) {
            Uri savedImageUri = Uri.parse(imageUriString);
            viewProfileImage.setImageURI(savedImageUri);
        } else {
            viewProfileImage.setImageResource(R.drawable.doc1); // Default image
        }
    }
}
