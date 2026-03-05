package com.asif.farmeta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class FarmerDashboardActivity extends AppCompatActivity {

    Button btnUploadCrop, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        btnUploadCrop = findViewById(R.id.btnUploadCrop);
        btnLogout = findViewById(R.id.btnLogout);

        btnUploadCrop.setOnClickListener(v -> {
            // Open UploadActivity to upload image
            startActivity(new Intent(FarmerDashboardActivity.this, UploadActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            // Logout and go to LoginActivity
            startActivity(new Intent(FarmerDashboardActivity.this, LoginActivity.class));
            finish();
        });
    }
}