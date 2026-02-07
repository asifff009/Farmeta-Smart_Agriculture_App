package com.asif.farmeta;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword;
    RadioGroup rgRole;
    Button btnSignup;

    // Emulator: 10.0.2.2, Real device: PC IP
    String SIGNUP_URL = "http://172.23.199.100/farmeta_api/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        rgRole = findViewById(R.id.rgRole);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String role = rgRole.getCheckedRadioButtonId() == R.id.rbFarmer ? "farmer" : "buyer";

            if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show();
                return;
            }

            signupUser(name,email,password,role);
        });
    }

    private void signupUser(String name, String email, String password, String role){
        StringRequest request = new StringRequest(Request.Method.POST, SIGNUP_URL,
                response -> {
                    try{
                        JSONObject obj = new JSONObject(response);
                        if(obj.getString("status").equals("success")){
                            Toast.makeText(this,"Signup successful",Toast.LENGTH_SHORT).show();
                            finish();
                        } else if(obj.getString("status").equals("exists")){
                            Toast.makeText(this,"Email already exists",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this,"Error during signup",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(this,"Parsing error",Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this,"Network error: "+error.getMessage(),Toast.LENGTH_LONG).show();
                }
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> map = new HashMap<>();
                map.put("name",name);
                map.put("email",email);
                map.put("password",password);
                map.put("role",role);
                return map;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
