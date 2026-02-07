package com.asif.farmeta;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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

public class CropGuideActivity extends AppCompatActivity {

    Spinner spinnerCropGuide;
    TextView tvFertilizerList;

    List<String> cropNames;
    String GET_CROPS_URL = "http://10.0.2.2/farmeta_api/get_crops.php";
    String GET_FERTILIZER_URL = "http://10.0.2.2/farmeta_api/get_crop_fertilizer.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_guide);

        spinnerCropGuide = findViewById(R.id.spinnerCropGuide);
        tvFertilizerList = findViewById(R.id.tvFertilizerList);

        cropNames = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cropNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCropGuide.setAdapter(adapter);

        fetchCrops();

        spinnerCropGuide.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cropName = spinnerCropGuide.getSelectedItem().toString();
                fetchFertilizers(cropName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void fetchCrops() {
        StringRequest request = new StringRequest(Request.Method.GET, GET_CROPS_URL,
                response -> {
                    try {
                        JSONArray arr = new JSONArray(response);
                        cropNames.clear();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            cropNames.add(obj.getString("crop_name"));
                        }
                        ((ArrayAdapter) spinnerCropGuide.getAdapter()).notifyDataSetChanged();
                    } catch (JSONException e) { e.printStackTrace(); }
                },
                error -> Toast.makeText(this, "Error fetching crops", Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(this).add(request);
    }

    private void fetchFertilizers(String cropName) {
        StringRequest request = new StringRequest(Request.Method.POST, GET_FERTILIZER_URL,
                response -> {
                    try {
                        JSONArray arr = new JSONArray(response);
                        StringBuilder sb = new StringBuilder();
                        for(int i=0;i<arr.length();i++){
                            JSONObject obj = arr.getJSONObject(i);
                            sb.append(obj.getString("fertilizer_name"))
                                    .append(" → ")
                                    .append(obj.getString("amount_per_acre"))
                                    .append(" kg/acre\n");
                        }
                        tvFertilizerList.setText(sb.toString());
                    } catch (JSONException e){ e.printStackTrace(); }
                },
                error -> Toast.makeText(this,"Error fetching fertilizer",Toast.LENGTH_SHORT).show()
        ){
            protected Map<String,String> getParams(){
                Map<String,String> map = new HashMap<>();
                map.put("crop_name",cropName);
                return map;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
