package com.asif.farmeta;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class MarketPriceActivity extends AppCompatActivity {

    Spinner spinnerCropMarket, spinnerMarket;
    TextView tvPriceList;

    List<String> cropNames, marketNames;
    String GET_CROPS_URL = "http://10.0.2.2/farmeta_api/get_crops.php";
    String GET_MARKETS_URL = "http://10.0.2.2/farmeta_api/get_markets.php";
    String GET_PRICES_URL = "http://10.0.2.2/farmeta_api/get_crop_price.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_price);

        spinnerCropMarket = findViewById(R.id.spinnerCropMarket);
        spinnerMarket = findViewById(R.id.spinnerMarket);
        tvPriceList = findViewById(R.id.tvPriceList);

        cropNames = new ArrayList<>();
        marketNames = new ArrayList<>();

        ArrayAdapter<String> cropAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cropNames);
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCropMarket.setAdapter(cropAdapter);

        ArrayAdapter<String> marketAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, marketNames);
        marketAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarket.setAdapter(marketAdapter);

        fetchCrops();
        fetchMarkets();

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) { fetchPrices(); }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        };

        spinnerCropMarket.setOnItemSelectedListener(listener);
        spinnerMarket.setOnItemSelectedListener(listener);
    }

    private void fetchCrops() {
        StringRequest request = new StringRequest(Request.Method.GET, GET_CROPS_URL,
                response -> { try { JSONArray arr=new JSONArray(response); cropNames.clear();
                    for(int i=0;i<arr.length();i++){ cropNames.add(arr.getJSONObject(i).getString("crop_name")); }
                    ((ArrayAdapter)spinnerCropMarket.getAdapter()).notifyDataSetChanged();
                } catch (JSONException e){e.printStackTrace();}}, error -> Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show());
        Volley.newRequestQueue(this).add(request);
    }

    private void fetchMarkets() {
        StringRequest request = new StringRequest(Request.Method.GET, GET_MARKETS_URL,
                response -> { try { JSONArray arr=new JSONArray(response); marketNames.clear();
                    for(int i=0;i<arr.length();i++){ marketNames.add(arr.getJSONObject(i).getString("market_name")); }
                    ((ArrayAdapter)spinnerMarket.getAdapter()).notifyDataSetChanged();
                } catch (JSONException e){e.printStackTrace();}}, error -> Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show());
        Volley.newRequestQueue(this).add(request);
    }

    private void fetchPrices() {
        String crop = spinnerCropMarket.getSelectedItem()!=null ? spinnerCropMarket.getSelectedItem().toString() : "";
        String market = spinnerMarket.getSelectedItem()!=null ? spinnerMarket.getSelectedItem().toString() : "";
        if(crop.isEmpty() || market.isEmpty()) return;

        StringRequest request = new StringRequest(Request.Method.POST, GET_PRICES_URL,
                response -> { try { JSONArray arr=new JSONArray(response);
                    StringBuilder sb=new StringBuilder();
                    for(int i=0;i<arr.length();i++){
                        JSONObject obj=arr.getJSONObject(i);
                        sb.append(obj.getString("date")).append(": ").append(obj.getString("price_per_kg")).append(" Tk/kg\n");
                    }
                    tvPriceList.setText(sb.toString());
                } catch (JSONException e){ e.printStackTrace();}},
                error -> Toast.makeText(this,"Error fetching prices",Toast.LENGTH_SHORT).show()
        ){
            protected Map<String, String> getParams(){
                Map<String,String> map=new HashMap<>();
                map.put("crop_name",crop);
                map.put("market_name",market);
                return map;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
