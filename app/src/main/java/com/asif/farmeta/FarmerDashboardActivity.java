package com.asif.farmeta;

import android.content.Intent;
import android.os.Bundle;
import android.text.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import java.util.*;

public class FarmerDashboardActivity extends AppCompatActivity {

    CropAdapter adapter;
    String selectedCategory = "All";
    Button btnLogout, btnUploadCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        RecyclerView rv = findViewById(R.id.rvCrops);
        EditText etSearch = findViewById(R.id.etSearch);

        // FIX: Initialize logout button correctly
        btnLogout = findViewById(R.id.btnLogout);
        btnUploadCrop = findViewById(R.id.btnUploadCrop);

        btnUploadCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FarmerDashboardActivity.this, ImageUpload.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to LoginActivity and finish current dashboard
                Intent logoutIntent = new Intent(FarmerDashboardActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                finish(); // prevent going back
            }
        });

        // Sample crop data
        List<CropModel> list = new ArrayList<>();
        list.add(new CropModel("Rice","Rice","Dhaka","John","150 Tk/kg","100 kg"));
        list.add(new CropModel("Tomato","Tomato","Khulna","Salam","80 Tk/kg","200 kg"));
        list.add(new CropModel("Potato","Potato","Rajshahi","Karim","60 Tk/kg","150 kg"));
        list.add(new CropModel("Onion","Onion","Sylhet","Akash","70 Tk/kg","12 kg"));
        list.add(new CropModel("Watermelon","Watermelon","Jamalpur","Kamal","70 Tk/piece","52 pieces"));
        list.add(new CropModel("Carrot","Carrot","Bogura","Amzad Hasan","45 Tk/kg","352 kg"));


        adapter = new CropAdapter(this, list);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        // Category buttons
        findViewById(R.id.btnAll).setOnClickListener(v -> {
            selectedCategory = "All";
            adapter.filter(etSearch.getText().toString(), selectedCategory);
        });

        findViewById(R.id.btnRice).setOnClickListener(v -> {
            selectedCategory = "Rice";
            adapter.filter(etSearch.getText().toString(), selectedCategory);
        });

        findViewById(R.id.btnTomato).setOnClickListener(v -> {
            selectedCategory = "Tomato";
            adapter.filter(etSearch.getText().toString(), selectedCategory);
        });

        findViewById(R.id.btnPotato).setOnClickListener(v -> {
            selectedCategory = "Potato";
            adapter.filter(etSearch.getText().toString(), selectedCategory);
        });

        // Search text watcher
        etSearch.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                adapter.filter(s.toString(), selectedCategory);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }
}