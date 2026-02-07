package com.asif.farmeta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin, btnSignup;

    String LOGIN_URL = "http://172.23.199.100/farmeta_api/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(v -> startActivity(new Intent(this, SignupActivity.class)));

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(email,password);
        });
    }

    private void loginUser(String email, String password){
        StringRequest request = new StringRequest(Request.Method.POST, LOGIN_URL,
                response -> {
                    try{
                        JSONObject obj = new JSONObject(response);
                        if(obj.getString("status").equals("success")){
                            String role = obj.getString("role");
                            if(role.equals("farmer")){
                                startActivity(new Intent(this, FarmerDashboardActivity.class));
                            } else if(role.equals("buyer")){
                                startActivity(new Intent(this, BuyerDashboardActivity.class));
                            }
                            finish();
                        } else {
                            Toast.makeText(this,"Invalid credentials",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(this,"Parsing error",Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this,"Network error",Toast.LENGTH_SHORT).show()
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> map = new HashMap<>();
                map.put("email",email);
                map.put("password",password);
                return map;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
