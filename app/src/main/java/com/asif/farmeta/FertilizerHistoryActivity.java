package com.asif.farmeta;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FertilizerHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<FertilizerModel> list;
    FertilizerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer_history);

        recyclerView = findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        adapter = new FertilizerAdapter(this, list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadHistory();
    }

    private void loadHistory() {
        // Use correct IP or domain for real device
        String url = "http://192.168.1.101/farmeta_api/get_history.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Log.d("API_RESPONSE", response);

                        JSONObject obj = new JSONObject(response);
                        if (!obj.has("data")) return;

                        JSONArray arr = obj.getJSONArray("data");
                        list.clear();

                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject o = arr.getJSONObject(i);

                            // Safely parse numbers
                            double landSize = o.optDouble("land_size", 0);
                            double nitrogen = o.optDouble("nitrogen", 0);
                            double phosphorus = o.optDouble("phosphorus", 0);
                            double potassium = o.optDouble("potassium", 0);

                            list.add(new FertilizerModel(
                                    o.optString("crop","N/A"),
                                    landSize,
                                    o.optString("unit","N/A"),
                                    nitrogen,
                                    phosphorus,
                                    potassium,
                                    o.optString("created_at","N/A")
                            ));
                        }

                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Parse error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "API error", Toast.LENGTH_SHORT).show();
                });

        // Use single RequestQueue
        Volley.newRequestQueue(this).add(request);
    }
}