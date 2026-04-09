package com.asif.farmeta;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class BuyerPost extends AppCompatActivity {

    EditText name, email, contact, location, crop, quantity, price;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_post);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        location = findViewById(R.id.location);
        crop = findViewById(R.id.crop);
        quantity = findViewById(R.id.quantity);
        price = findViewById(R.id.price);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(v -> uploadData());
    }

    private void uploadData() {

        String url = "http://192.168.1.101/farmeta_api/add_buyer_post.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(this, "Posted Successfully", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        ){
            @Override
            protected java.util.Map<String,String> getParams(){
                java.util.Map<String,String> params = new java.util.HashMap<>();

                params.put("name", name.getText().toString());
                params.put("email", email.getText().toString());
                params.put("contact", contact.getText().toString());
                params.put("location", location.getText().toString());
                params.put("crop", crop.getText().toString());
                params.put("quantity", quantity.getText().toString());
                params.put("price", price.getText().toString());

                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}