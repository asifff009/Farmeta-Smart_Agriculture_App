package com.asif.farmeta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.HorizontalScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class FarmerDashboardActivity extends AppCompatActivity {

    Button btnUploadCrop, btnLogout;
    HorizontalScrollView scrollView;

    Handler handler = new Handler();

    int currentIndex = 0;
    int imageWidth = 260; // image width + margin
    int totalImages = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        btnUploadCrop = findViewById(R.id.btnUploadCrop);
        btnLogout = findViewById(R.id.btnLogout);
        scrollView = findViewById(R.id.imageSlider);

        btnUploadCrop.setOnClickListener(v ->
                startActivity(new Intent(FarmerDashboardActivity.this, UploadActivity.class))
        );

        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(FarmerDashboardActivity.this, LoginActivity.class));
            finish();
        });

        startSlider();
    }

    private void startSlider() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                currentIndex++;

                if(currentIndex >= totalImages){
                    currentIndex = 0;
                }

                int scrollPos = currentIndex * imageWidth;

                scrollView.smoothScrollTo(scrollPos,0);

                handler.postDelayed(this,2000);
            }
        },2000);
    }
}