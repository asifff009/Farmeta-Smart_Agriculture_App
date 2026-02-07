package com.asif.farmeta;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etPhone, etPassword, etLocation, etLandSize, etShopName;
    Spinner spinnerRole;
    Button btnRegister;

    String URL = "http://10.0.2.2/farmeta_api/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etLocation = findViewById(R.id.etLocation);
        etLandSize = findViewById(R.id.etLandSize);
        etShopName = findViewById(R.id.etShopName);
        spinnerRole = findViewById(R.id.spinnerRole);
        btnRegister = findViewById(R.id.btnRegister);

        // Spinner setup
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"farmer", "buyer"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        // Initial visibility
        toggleFields("farmer");

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String role = spinnerRole.getSelectedItem().toString();
                toggleFields(role);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void toggleFields(String role) {
        if (role.equals("farmer")) {
            etLocation.setVisibility(View.VISIBLE);
            etLandSize.setVisibility(View.VISIBLE);
            etShopName.setVisibility(View.GONE);
        } else {
            etLocation.setVisibility(View.GONE);
            etLandSize.setVisibility(View.GONE);
            etShopName.setVisibility(View.VISIBLE);
        }
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String role = spinnerRole.getSelectedItem().toString();

        String location = etLocation.getText().toString().trim();
        String landSize = etLandSize.getText().toString().trim();
        String shopName = etShopName.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.getString("status").equals("success")) {
                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            finish(); // Go back to login
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> map = new HashMap<>();
                map.put("name", name);
                map.put("phone", phone);
                map.put("password", password);
                map.put("role", role);

                if (role.equals("farmer")) {
                    map.put("location", location);
                    map.put("land_size", landSize);
                } else {
                    map.put("shop_name", shopName);
                }

                return map;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
