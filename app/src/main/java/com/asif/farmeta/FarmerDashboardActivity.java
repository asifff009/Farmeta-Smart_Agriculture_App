package com.asif.farmeta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class FarmerDashboardActivity extends AppCompatActivity {

    Button btnUploadCrop, btnLogout, btnBuyerGig;
    Button btnFertilizer, btnFertilizerHistory;

    HorizontalScrollView scrollView;
    LinearLayout pesticide;

    Spinner spinnerDistrict;
    TextView tvCity, tvTemp, tvHumidity, tvWind, tvDesc;

    Handler handler = new Handler();
    int currentIndex = 0;
    int imageWidth = 290 + 12; // slider image width + margin
    int totalImages = 4;

    final int REFRESH_INTERVAL = 120000; // 2 minutes in ms
    final String[] selectedCity = {""}; // selected city for auto-refresh

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        //detectDesease = findViewById(R.id.detectDesease);

        // Views
        btnUploadCrop = findViewById(R.id.btnUploadCrop);
        btnLogout = findViewById(R.id.btnLogout);
        scrollView = findViewById(R.id.imageSlider);
        pesticide = findViewById(R.id.pesticide);
        btnBuyerGig = findViewById(R.id.btnBuyerGig);

        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        tvCity = findViewById(R.id.tvCity);
        tvTemp = findViewById(R.id.tvTemp);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvWind = findViewById(R.id.tvWind);
        tvDesc = findViewById(R.id.tvDesc);

        //detectDesease.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //   public void onClick(View view) {
        //    Intent detectIntent = new Intent(FarmerDashboardActivity.this, MarketActivity.class);
        //  startActivity(detectIntent);
        //  }
        //   });

        btnFertilizer = findViewById(R.id.btnFertilizer);
        btnFertilizerHistory = findViewById(R.id.btnFertilizerHistory);

        btnBuyerGig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gigIntent = new Intent(FarmerDashboardActivity.this, BuyerGigActivity.class);
                startActivity(gigIntent);
            }
        });

        // 3️⃣ Click Listeners
        btnFertilizer.setOnClickListener(v -> {
            Intent intent = new Intent(FarmerDashboardActivity.this, FertilizerCalculatorActivity.class);
            startActivity(intent);
        });

        btnFertilizerHistory.setOnClickListener(v -> {
            Intent intent = new Intent(FarmerDashboardActivity.this, FertilizerHistoryActivity.class);
            startActivity(intent);
        });

        // Spinner - 64 Bangladesh districts
        String[] districts = {
                "Dhaka","Gazipur","Narsingdi","Tangail","Manikganj","Mymensingh","Sherpur","Netrokona",
                "Faridpur","Rajbari","Gopalganj","Shariatpur","Madaripur","Kishoreganj","Comilla","Chandpur",
                "Brahmanbaria","Noakhali","Lakshmipur","Feni","Khulna","Jessore","Satkhira","Bagerhat","Narail",
                "Magura","Chuadanga","Kushtia","Meherpur","Barishal","Patuakhali","Bhola","Jhalokathi","Pirojpur",
                "Chittagong","Cox's Bazar","Bandarban","Rangamati","Khagrachhari","Feni","Lakshmipur","Noakhali",
                "Sylhet","Moulvibazar","Habiganj","Sunamganj","Rangpur","Dinajpur","Thakurgaon","Panchagarh","Nilphamari",
                "Lalmonirhat","Kurigram","Gaibandha","Bogura","Joypurhat","Naogaon","Natore","Pabna","Sirajganj",
                "Rajshahi","Chapainawabganj","Kushtia","Jhenaidah","Magura","Jamalpur"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(adapter);

        // Spinner listener
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity[0] = districts[position];
                updateWeather(selectedCity[0]); // immediate fetch on selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Auto-refresh weather every 2 minutes
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!selectedCity[0].isEmpty()) {
                    updateWeather(selectedCity[0]);
                }
                handler.postDelayed(this, REFRESH_INTERVAL);
            }
        }, REFRESH_INTERVAL);

        // Button actions
        btnUploadCrop.setOnClickListener(v ->
                startActivity(new android.content.Intent(FarmerDashboardActivity.this, UploadActivity.class))
        );

        btnLogout.setOnClickListener(v -> {
            startActivity(new android.content.Intent(FarmerDashboardActivity.this, LoginActivity.class));
            finish();
        });

        pesticide.setOnClickListener(v ->
                startActivity(new android.content.Intent(FarmerDashboardActivity.this, PesticidePage.class))
        );

        startSlider();
    }

    // Slider auto-scroll
    private void startSlider() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentIndex++;
                if (currentIndex >= totalImages) currentIndex = 0;
                int scrollPos = currentIndex * imageWidth;
                scrollView.smoothScrollTo(scrollPos, 0);
                handler.postDelayed(this, 2000);
            }
        }, 2000);
    }

    // Fetch weather from DB and update DB
    private void updateWeather(String city){
        // 1️⃣ Get latest weather from DB
        String url = "http://192.168.1.101/farmeta_api/get_weather.php?city=" + city; // emulator IP
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        tvCity.setText("City: " + obj.optString("city","N/A"));
                        tvTemp.setText("Temp: " + obj.optString("temperature","N/A") + "°C");
                        tvHumidity.setText("Humidity: " + obj.optString("humidity","N/A") + "%");
                        tvWind.setText("Wind: " + obj.optString("wind_speed","N/A") + " m/s");
                        tvDesc.setText("Description: " + obj.optString("description","N/A"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );
        Volley.newRequestQueue(this).add(request);

        // 2️⃣ Update weather in DB from API
        String updateUrl = "http://192.168.1.101/farmeta_api/update_weather.php?city=" + city;
        StringRequest updateRequest = new StringRequest(Request.Method.GET, updateUrl,
                resp -> {},
                error -> error.printStackTrace()
        );
        Volley.newRequestQueue(this).add(updateRequest);
    }
}