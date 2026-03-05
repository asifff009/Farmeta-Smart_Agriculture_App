package com.asif.farmeta;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class BuyerDashboardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CropModel> list;
    CropAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_dashboard);

        recyclerView = findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        adapter = new CropAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadCrops();
    }

    private void loadCrops(){
        String url = "http://192.168.1.100/farmeta_api/get_crops.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try{
                        JSONArray array = new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            JSONObject obj = array.getJSONObject(i);
                            CropModel model = new CropModel(
                                    obj.getString("first_name"),
                                    obj.getString("middle_name"),
                                    obj.getString("last_name"),
                                    obj.getString("crop_name"),
                                    obj.getString("quantity"),
                                    obj.getString("price"),
                                    obj.getString("contact"),
                                    obj.getString("address"),
                                    obj.getString("image")
                            );
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    } catch(Exception e){ e.printStackTrace(); }
                },
                error -> error.printStackTrace()
        );

        Volley.newRequestQueue(this).add(request);
    }
}