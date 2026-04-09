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
import com.asif.farmeta.FertilizerAdapter;
import com.asif.farmeta.FertilizerModel;
import com.asif.farmeta.R;

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
        String url = "http://10.0.2.2/farmeta_api/get_history.php";

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
                            list.add(new FertilizerModel(
                                    o.getString("crop"),
                                    o.getDouble("land_size"),
                                    o.getString("unit"),
                                    o.getDouble("nitrogen"),
                                    o.getDouble("phosphorus"),
                                    o.getDouble("potassium"),
                                    o.getString("created_at")
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

        Volley.newRequestQueue(this).add(request);
    }
}