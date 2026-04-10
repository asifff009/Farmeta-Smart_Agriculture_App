package com.asif.farmeta;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.*;

import java.util.*;

public class GigSeeBuyer extends AppCompatActivity {

    RecyclerView recyclerView;
    BuyerPostAdapter adapter;
    ArrayList<BuyerPostModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_see_buyer);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new BuyerPostAdapter(this,list);
        recyclerView.setAdapter(adapter);

        loadData();
    }

    private void loadData(){

        String url = "http://192.168.1.101/farmeta_api/get_buyer_posts.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try{
                        JSONArray array = new JSONArray(response);
                        list.clear();

                        for(int i=0;i<array.length();i++){
                            JSONObject obj = array.getJSONObject(i);

                            list.add(new BuyerPostModel(
                                    obj.getString("name"),
                                    obj.getString("email"),
                                    obj.getString("contact"),
                                    obj.getString("location"),
                                    obj.getString("crop"),
                                    obj.getString("quantity"),
                                    obj.getString("price")
                            ));
                        }

                        adapter.notifyDataSetChanged();

                    }catch(Exception e){e.printStackTrace();}
                },
                error -> error.printStackTrace()
        );

        Volley.newRequestQueue(this).add(request);
    }
}