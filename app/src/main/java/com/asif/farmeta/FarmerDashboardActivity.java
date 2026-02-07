package com.asif.farmeta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FarmerDashboardActivity extends AppCompatActivity {

    private RecyclerView rvMyCrops;
    private Button btnAddCrop;
    private List<CropModel> cropList;
    private CropAdapter cropAdapter;

    private final String FARMER_ID = "1"; // TODO: replace with login session
    private final String GET_MY_CROPS_URL = "http://10.0.2.2/farmeta_api/get_my_crops.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        rvMyCrops = findViewById(R.id.rvMyCrops);
        btnAddCrop = findViewById(R.id.btnAddCrop);

        cropList = new ArrayList<>();
        cropAdapter = new CropAdapter(this, cropList);
        rvMyCrops.setLayoutManager(new LinearLayoutManager(this));
        rvMyCrops.setAdapter(cropAdapter);

        btnAddCrop.setOnClickListener(v -> startActivity(new Intent(this, AddCropActivity.class)));

        fetchMyCrops();
    }

    private void fetchMyCrops() {
        StringRequest request = new StringRequest(Request.Method.POST, GET_MY_CROPS_URL,
                response -> {
                    try {
                        cropList.clear();
                        JSONArray arr = new JSONArray(response);
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);

                            String cropId = obj.getString("fc_id");
                            String cropName = obj.getString("crop_name");
                            String status = obj.getString("status");
                            String expectedYield = obj.getString("expected_yield");
                            String startDate = obj.getString("start_date");

                            CropModel crop = new CropModel(cropId, cropName, status, expectedYield, startDate);
                            cropList.add(crop);
                        }
                        cropAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error fetching crops", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected java.util.Map<String, String> getParams() {
                java.util.Map<String, String> map = new java.util.HashMap<>();
                map.put("farmer_id", FARMER_ID);
                return map;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
