package com.asif.farmeta;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class FertilizerCalculatorActivity extends AppCompatActivity {

    Spinner spinnerCrop, spinnerUnit;
    EditText etLand;
    TextView tvResult;
    Button btnCalculate;

    String[] crops = {"Rice","Wheat","Corn"};
    String[] units = {"Hectare","Acre","m2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer_calculator);

        spinnerCrop = findViewById(R.id.spinnerCrop);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        etLand = findViewById(R.id.etLand);
        tvResult = findViewById(R.id.tvResult);
        btnCalculate = findViewById(R.id.btnCalculate);

        spinnerCrop.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, crops));
        spinnerUnit.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units));

        btnCalculate.setOnClickListener(v -> calculate());
    }

    private void calculate(){

        String crop = spinnerCrop.getSelectedItem().toString();
        String unit = spinnerUnit.getSelectedItem().toString();
        double land = Double.parseDouble(etLand.getText().toString());

        // Convert সব unit → hectare
        if(unit.equals("Acre")) land *= 0.4047;
        if(unit.equals("m2")) land /= 10000;

        // Simple fertilizer logic
        double N = land * 100;
        double P = land * 50;
        double K = land * 30;

        tvResult.setText("N: "+N+" P: "+P+" K: "+K);

        saveToDatabase(crop, land, unit, N, P, K);
    }

    private void saveToDatabase(String crop,double land,String unit,double n,double p,double k){

        String url = "http://192.168.1.101/farmeta_api/save_fertilizer.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(this,"Saved!",Toast.LENGTH_SHORT).show(),
                error -> error.printStackTrace()
        ){
            protected Map<String,String> getParams(){
                Map<String,String> map = new HashMap<>();
                map.put("crop",crop);
                map.put("land_size",String.valueOf(land));
                map.put("unit",unit);
                map.put("nitrogen",String.valueOf(n));
                map.put("phosphorus",String.valueOf(p));
                map.put("potassium",String.valueOf(k));
                return map;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}