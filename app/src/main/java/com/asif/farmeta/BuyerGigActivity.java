package com.asif.farmeta;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuyerGigActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<BuyerModel> list;
    BuyerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_gig);

        recyclerView = findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        adapter = new BuyerAdapter(this, list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadData();
    }

    private void loadData(){
        String url = "http://192.168.1.101/farmeta_api/get_buyer_posts.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try{
                        JSONArray arr = new JSONArray(response);
                        list.clear();

                        for(int i=0;i<arr.length();i++){
                            JSONObject o = arr.getJSONObject(i);
                            list.add(new BuyerModel(
                                    o.getString("name"),
                                    o.getString("crop"),
                                    o.getString("quantity"),
                                    o.getString("price"),
                                    o.getString("contact"),
                                    o.getString("location")
                            ));
                        }

                        adapter.notifyDataSetChanged();

                    } catch(Exception e){ e.printStackTrace(); }
                },
                error -> error.printStackTrace()
        );

        Volley.newRequestQueue(this).add(request);
    }
}