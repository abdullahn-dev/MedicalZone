package com.example.medicalzone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRegisterActivity extends AppCompatActivity {

    private EditText etPin;
    private Button btnLogin;
    private int attemptCount = 0; // To track the number of failed attempts
    private static final String VALID_PIN = "1508"; // The valid pin
    private static final int MAX_ATTEMPTS = 3; // Maximum number of allowed attempts
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_auth);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        etPin = findViewById(R.id.etPin); // Assuming you have an EditText with ID etPin
        btnLogin = findViewById(R.id.btnLogin); // Assuming you have a Button with ID btnLogin

        // Create Notification Channel (only for devices running Android 8.0 and above)
        createNotificationChannel();

        // Login Button Action
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = etPin.getText().toString().trim();

                // Check if the pin field is empty
                if (pin.isEmpty()) {
                    Toast.makeText(LoginRegisterActivity.this, "Please enter the PIN", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the entered pin matches the valid pin
                    if (pin.equals(VALID_PIN)) {
                        Toast.makeText(LoginRegisterActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Firebase Authentication: Check if a user is already logged in
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if (currentUser == null) {
                            // If no user is logged in, you can either register them or log them in
                            // For example, create a user session and redirect to the dashboard
                            Intent intent = new Intent(LoginRegisterActivity.this, AdminDashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Redirect to the dashboard if already authenticated via Firebase
                            Intent intent = new Intent(LoginRegisterActivity.this, AdminDashboardActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        // Increment the attempt count
                        attemptCount++;

                        // Check if the max attempts are reached
                        if (attemptCount >= MAX_ATTEMPTS) {
                            // Alert the user and send a notification
                            Toast.makeText(LoginRegisterActivity.this, "Maximum attempts reached. Access denied.", Toast.LENGTH_LONG).show();
                            sendNotification();
                            finish(); // Close the app or move to another activity
                        } else {
                            // Inform the user about remaining attempts
                            int remainingAttempts = MAX_ATTEMPTS - attemptCount;
                            Toast.makeText(LoginRegisterActivity.this, "Invalid PIN. " + remainingAttempts + " attempt(s) left.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    // Function to create a notification channel for devices with Android 8.0 (API level 26) and above
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Login Attempt Channel";
            String description = "Notifications for login attempt failures";
            int importance = NotificationManager.IMPORTANCE_HIGH; // Set importance level
            NotificationChannel channel = new NotificationChannel(
                    "LOGIN_ATTEMPT_CHANNEL", name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    // Function to send a notification when max attempts are reached
    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "LOGIN_ATTEMPT_CHANNEL")
                .setSmallIcon(R.drawable.img) // Replace with your app icon
                .setContentTitle("Login Attempt Failed")
                .setContentText("3 failed login attempts. Access denied.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Check if notification permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            return;
        }

        // Send the notification
        notificationManager.notify(1001, builder.build()); // 1001 is the notification ID
    }

    // Handle the result of permission requests
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, send the notification
                sendNotification();
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(this, "Notification permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
