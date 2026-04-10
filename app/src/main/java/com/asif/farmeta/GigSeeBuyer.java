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

public class GigSeeBuyer extends AppCompatActivity {

    RecyclerView recyclerView;
    BuyerPostAdapter adapter;
    ArrayList<BuyerPostModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_see_buyer);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new BuyerPostAdapter(this, list);
        recyclerView.setAdapter(adapter);

        loadData();
    }

    private void loadData() {

        String url = "http://192.168.1.101/farmeta_api/get_buyer_posts.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {

                    try {

                        JSONArray array = new JSONArray(response);
                        list.clear();

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject obj = array.getJSONObject(i);

                            // 🔵 crop items list
                            ArrayList<BuyerPostModel.CropItem> items = new ArrayList<>();

                            JSONArray itemsArr = obj.getJSONArray("items");

                            for (int j = 0; j < itemsArr.length(); j++) {

                                JSONObject it = itemsArr.getJSONObject(j);

                                items.add(new BuyerPostModel.CropItem(
                                        it.getString("crop_name"),
                                        it.getString("quantity"),
                                        it.getString("price")
                                ));
                            }

                            // 🟢 main object add
                            list.add(new BuyerPostModel(
                                    obj.getString("id"),
                                    obj.getString("name"),
                                    obj.getString("email"),
                                    obj.getString("contact"),
                                    obj.getString("location"),
                                    items
                            ));
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );

        Volley.newRequestQueue(this).add(request);
    }
}