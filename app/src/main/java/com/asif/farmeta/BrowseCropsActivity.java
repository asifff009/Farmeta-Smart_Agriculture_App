package com.asif.farmeta;

import android.os.Bundle;
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

public class BrowseCropsActivity extends AppCompatActivity {

    RecyclerView rvAvailableCrops;
    List<CropModel> cropList;
    CropAdapter cropAdapter;

    String GET_AVAILABLE_CROPS_URL = "http://10.0.2.2/farmeta_api/get_available_crops.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_crops);

        rvAvailableCrops = findViewById(R.id.rvAvailableCrops);
        rvAvailableCrops.setLayoutManager(new LinearLayoutManager(this));

        cropList = new ArrayList<>();
        cropAdapter = new CropAdapter(this, cropList, true); // true = Buyer mode
        rvAvailableCrops.setAdapter(cropAdapter);

        fetchAvailableCrops();
    }

    private void fetchAvailableCrops() {
        StringRequest request = new StringRequest(Request.Method.GET, GET_AVAILABLE_CROPS_URL,
                response -> {
                    try {
                        cropList.clear();
                        JSONArray arr = new JSONArray(response);
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);

                            // Make sure your backend sends crop_id as well
                            String cropId = obj.getString("fc_id"); // farmer_crop id
                            String cropName = obj.getString("crop_name");
                            String status = obj.getString("status");
                            String expectedYield = obj.getString("expected_yield");
                            String startDate = obj.getString("start_date");
                            String farmerId = obj.getString("farmer_id");
                            String farmerName = obj.getString("farmer_name");

                            CropModel crop = new CropModel(
                                    cropId,
                                    cropName,
                                    status,
                                    expectedYield,
                                    startDate,
                                    farmerId,
                                    farmerName
                            );
                            cropList.add(crop);
                        }
                        cropAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error fetching crops", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }
}
