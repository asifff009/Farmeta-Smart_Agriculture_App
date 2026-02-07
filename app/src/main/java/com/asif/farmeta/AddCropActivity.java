package com.asif.farmeta;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCropActivity extends AppCompatActivity {

    Spinner spinnerCrop;
    EditText etStartDate, etExpectedYield;
    Button btnSaveCrop;

    List<String> cropNames;
    String farmerId = "1"; // login session
    String GET_CROP_URL = "http://10.0.2.2/farmeta_api/get_crops.php";
    String ADD_CROP_URL = "http://10.0.2.2/farmeta_api/add_farmer_crop.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);

        spinnerCrop = findViewById(R.id.spinnerCrop);
        etStartDate = findViewById(R.id.etStartDate);
        etExpectedYield = findViewById(R.id.etExpectedYield);
        btnSaveCrop = findViewById(R.id.btnSaveCrop);

        cropNames = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cropNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCrop.setAdapter(adapter);

        fetchCrops();

        btnSaveCrop.setOnClickListener(v -> saveCrop());
    }

    private void fetchCrops() {
        StringRequest request = new StringRequest(Request.Method.GET, GET_CROP_URL,
                response -> {
                    try {
                        JSONArray arr = new JSONArray(response);
                        cropNames.clear();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            cropNames.add(obj.getString("crop_name"));
                        }
                        ((ArrayAdapter) spinnerCrop.getAdapter()).notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error fetching crops", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }

    private void saveCrop() {
        String cropName = spinnerCrop.getSelectedItem().toString();
        String startDate = etStartDate.getText().toString().trim();
        String expectedYield = etExpectedYield.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, ADD_CROP_URL,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.getString("status").equals("success")){
                            Toast.makeText(this, "Crop added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error adding crop", Toast.LENGTH_SHORT).show()
        ){
            protected Map<String,String> getParams(){
                Map<String,String> map = new HashMap<>();
                map.put("farmer_id", farmerId);
                map.put("crop_name", cropName);
                map.put("start_date", startDate);
                map.put("expected_yield", expectedYield);
                return map;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
