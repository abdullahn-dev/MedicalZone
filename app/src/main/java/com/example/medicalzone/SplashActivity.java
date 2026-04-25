package com.example.medicalzone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Add a delay of 2 seconds before navigating to MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent to navigate to MainActivity after the delay
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close SplashActivity so the user can't return to it
            }
        }, 1000); // 2000 milliseconds = 2 seconds
    }
}
