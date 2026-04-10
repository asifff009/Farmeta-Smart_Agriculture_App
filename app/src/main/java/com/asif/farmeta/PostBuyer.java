package com.asif.farmeta;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import java.util.HashMap;
import java.util.Map;

public class PostBuyer extends AppCompatActivity {

    EditText name, email, contact, location, crop, quantity, price;
    Button uploadBtn, updateBtn, buyerLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_buyer);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        location = findViewById(R.id.location);
        crop = findViewById(R.id.crop);
        quantity = findViewById(R.id.quantity);
        price = findViewById(R.id.price);

        uploadBtn = findViewById(R.id.uploadBtn);
        updateBtn = findViewById(R.id.updateBtn);
        buyerLogout = findViewById(R.id.buyerLogout);

        buyerLogout.setOnClickListener(v -> {
            startActivity(new Intent(PostBuyer.this, LoginActivity.class));
            finish();
        });

        uploadBtn.setOnClickListener(v -> {
            if(validateFields()){
                uploadData();
            }
        });

        updateBtn.setOnClickListener(v -> showUpdateDialog());
    }

    // ✅ VALIDATION
    private boolean validateFields(){

        if(name.getText().toString().trim().isEmpty() ||
                email.getText().toString().trim().isEmpty() ||
                contact.getText().toString().trim().isEmpty() ||
                location.getText().toString().trim().isEmpty() ||
                crop.getText().toString().trim().isEmpty() ||
                quantity.getText().toString().trim().isEmpty() ||
                price.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 🟢 INSERT
    private void uploadData(){

        String url = "http://192.168.1.101/farmeta_api/insert_buyer_post.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(this,"Uploaded",Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this,"Upload Failed",Toast.LENGTH_SHORT).show()
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();

                params.put("name", name.getText().toString().trim());
                params.put("email", email.getText().toString().trim());
                params.put("contact", contact.getText().toString().trim());
                params.put("location", location.getText().toString().trim());
                params.put("crop", crop.getText().toString().trim());
                params.put("quantity", quantity.getText().toString().trim());
                params.put("price", price.getText().toString().trim());

                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    // 🔵 UPDATE DIALOG
    private void showUpdateDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Buyer Post");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_update_buyer, null);
        builder.setView(view);

        EditText dName = view.findViewById(R.id.d_name);
        EditText dEmail = view.findViewById(R.id.d_email);
        EditText dContact = view.findViewById(R.id.d_contact);
        EditText dLocation = view.findViewById(R.id.d_location);
        EditText dCrop = view.findViewById(R.id.d_crop);
        EditText dQuantity = view.findViewById(R.id.d_quantity);
        EditText dPrice = view.findViewById(R.id.d_price);
        Button dUpdate = view.findViewById(R.id.d_updateBtn);

        // 🔥 AUTO FILL OLD DATA
        dName.setText(name.getText().toString());
        dEmail.setText(email.getText().toString());
        dContact.setText(contact.getText().toString());
        dLocation.setText(location.getText().toString());
        dCrop.setText(crop.getText().toString());
        dQuantity.setText(quantity.getText().toString());
        dPrice.setText(price.getText().toString());

        AlertDialog dialog = builder.create();
        dialog.show();

        dUpdate.setOnClickListener(v -> {

            String url = "http://192.168.1.101/farmeta_api/update_buyer_post.php";

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    response -> {
                        Toast.makeText(this,"Updated Successfully",Toast.LENGTH_SHORT).show();

                        // 🔥 update main screen fields
                        name.setText(dName.getText().toString());
                        email.setText(dEmail.getText().toString());
                        contact.setText(dContact.getText().toString());
                        location.setText(dLocation.getText().toString());
                        crop.setText(dCrop.getText().toString());
                        quantity.setText(dQuantity.getText().toString());
                        price.setText(dPrice.getText().toString());

                        dialog.dismiss();
                    },
                    error -> Toast.makeText(this,"Update Failed",Toast.LENGTH_SHORT).show()
            ){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<>();

                    params.put("name", dName.getText().toString().trim());
                    params.put("email", dEmail.getText().toString().trim());
                    params.put("contact", dContact.getText().toString().trim());
                    params.put("location", dLocation.getText().toString().trim());
                    params.put("crop", dCrop.getText().toString().trim());
                    params.put("quantity", dQuantity.getText().toString().trim());
                    params.put("price", dPrice.getText().toString().trim());

                    return params;
                }
            };

            Volley.newRequestQueue(this).add(request);
        });
    }
}