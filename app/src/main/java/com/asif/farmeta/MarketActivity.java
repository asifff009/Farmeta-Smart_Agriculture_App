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

public class MarketActivity extends AppCompatActivity {

    RecyclerView recycler;
    ArrayList<MarketModel> list;
    MarketAdapter adapter;

    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_market);

        recycler = findViewById(R.id.recycler);
        list = new ArrayList<>();
        adapter = new MarketAdapter(this,list);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        loadData();
    }

    private void loadData(){

        String url = "http://192.168.1.101/farmeta_api/get_market_price.php";

        StringRequest req = new StringRequest(Request.Method.GET, url,
                res -> {
                    try{
                        JSONArray arr = new JSONArray(res);
                        list.clear();

                        for(int i=0;i<arr.length();i++){
                            JSONObject o = arr.getJSONObject(i);

                            list.add(new MarketModel(
                                    o.getString("crop_name"),
                                    o.getString("price"),
                                    o.getString("district"),
                                    o.getString("date")
                            ));
                        }

                        adapter.notifyDataSetChanged();

                    }catch(Exception e){ e.printStackTrace(); }
                },
                err -> err.printStackTrace()
        );

        Volley.newRequestQueue(this).add(req);
    }
}