package com.asif.farmeta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

public class BuyerDashboardActivity extends AppCompatActivity {

    private Button btnBrowseCrops;
    private RecyclerView rvBuyerOrders;
    private List<OrderModel> orderList;
    private OrderAdapter orderAdapter;

    private final String BUYER_ID = "1"; // TODO: replace with login session
    private final String GET_ORDERS_URL = "http://10.0.2.2/farmeta_api/get_buyer_orders.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_dashboard);

        btnBrowseCrops = findViewById(R.id.btnBrowseCrops);
        rvBuyerOrders = findViewById(R.id.rvBuyerOrders);

        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderList);

        rvBuyerOrders.setLayoutManager(new LinearLayoutManager(this));
        rvBuyerOrders.setAdapter(orderAdapter);

        btnBrowseCrops.setOnClickListener(v -> startActivity(new Intent(this, BrowseCropsActivity.class)));

        fetchOrders();
    }

    private void fetchOrders() {
        StringRequest request = new StringRequest(Request.Method.POST, GET_ORDERS_URL,
                response -> {
                    try {
                        orderList.clear();
                        JSONArray arr = new JSONArray(response);
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);

                            String cropName = obj.getString("crop_name");
                            String quantity = obj.getString("quantity");
                            String price = obj.getString("price");
                            String farmerName = obj.getString("farmer_name");

                            OrderModel order = new OrderModel(cropName, quantity, price, farmerName);
                            orderList.add(order);
                        }
                        orderAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error fetching orders", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected java.util.Map<String, String> getParams() {
                java.util.Map<String, String> map = new java.util.HashMap<>();
                map.put("buyer_id", BUYER_ID);
                return map;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
