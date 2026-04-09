package com.asif.farmeta;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class BuyerPost extends AppCompatActivity {

    EditText name, email, contact, location, crop, quantity, price;
    Button submit, update, buyerBtnBack, LogoutBtn;

    @SuppressLint("WrongViewCast")
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
        update = findViewById(R.id.update);
        buyerBtnBack = findViewById(R.id.buyerBtnBack);
        LogoutBtn = findViewById(R.id.LogoutBtn);

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logBtttttn = new Intent(BuyerPost.this, BuyerDashboardActivity.class);
                startActivity(logBtttttn);
            }
        });

        buyerBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buyerBackIntent = new Intent(BuyerPost.this, BuyerDashboardActivity.class);
                startActivity(buyerBackIntent);
            }
        });

        submit.setOnClickListener(v -> {
            if (areFieldsFilled()) {
                uploadData();
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(v -> {
            if (areFieldsFilled()) {
                showUpdateDialog();
            } else {
                Toast.makeText(this, "Please fill all fields before updating", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Check if all fields are filled
    private boolean areFieldsFilled() {
        return !TextUtils.isEmpty(name.getText().toString().trim()) &&
                !TextUtils.isEmpty(email.getText().toString().trim()) &&
                !TextUtils.isEmpty(contact.getText().toString().trim()) &&
                !TextUtils.isEmpty(location.getText().toString().trim()) &&
                !TextUtils.isEmpty(crop.getText().toString().trim()) &&
                !TextUtils.isEmpty(quantity.getText().toString().trim()) &&
                !TextUtils.isEmpty(price.getText().toString().trim());
    }

    // Upload new buyer info
    private void uploadData() {
        String url = "http://192.168.1.101/farmeta_api/add_buyer_post.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(this, "Posted Successfully", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
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

    // Show dialog to update info
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Buyer Info");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_buyer_post, null);
        builder.setView(dialogView);

        EditText dName = dialogView.findViewById(R.id.name);
        EditText dEmail = dialogView.findViewById(R.id.email);
        EditText dContact = dialogView.findViewById(R.id.contact);
        EditText dLocation = dialogView.findViewById(R.id.location);
        EditText dCrop = dialogView.findViewById(R.id.crop);
        EditText dQuantity = dialogView.findViewById(R.id.quantity);
        EditText dPrice = dialogView.findViewById(R.id.price);

        // pre-fill existing values
        dName.setText(name.getText().toString());
        dEmail.setText(email.getText().toString());
        dContact.setText(contact.getText().toString());
        dLocation.setText(location.getText().toString());
        dCrop.setText(crop.getText().toString());
        dQuantity.setText(quantity.getText().toString());
        dPrice.setText(price.getText().toString());

        builder.setPositiveButton("Update", (dialog, which) -> {
            // Validate fields in dialog
            if (TextUtils.isEmpty(dName.getText().toString().trim()) ||
                    TextUtils.isEmpty(dEmail.getText().toString().trim()) ||
                    TextUtils.isEmpty(dContact.getText().toString().trim()) ||
                    TextUtils.isEmpty(dLocation.getText().toString().trim()) ||
                    TextUtils.isEmpty(dCrop.getText().toString().trim()) ||
                    TextUtils.isEmpty(dQuantity.getText().toString().trim()) ||
                    TextUtils.isEmpty(dPrice.getText().toString().trim())) {

                Toast.makeText(this, "All fields are required to update", Toast.LENGTH_SHORT).show();
                return;
            }

            // update main fields
            name.setText(dName.getText().toString());
            email.setText(dEmail.getText().toString());
            contact.setText(dContact.getText().toString());
            location.setText(dLocation.getText().toString());
            crop.setText(dCrop.getText().toString());
            quantity.setText(dQuantity.getText().toString());
            price.setText(dPrice.getText().toString());

            updateDatabase(dName.getText().toString(), dEmail.getText().toString(),
                    dContact.getText().toString(), dLocation.getText().toString(),
                    dCrop.getText().toString(), dQuantity.getText().toString(),
                    dPrice.getText().toString());
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    // Send updated info to DB
    private void updateDatabase(String name, String email, String contact, String location,
                                String crop, String quantity, String price) {

        String url = "http://192.168.1.101/farmeta_api/update_buyer_post.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this, "Update Error", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("contact", contact);
                params.put("location", location);
                params.put("crop", crop);
                params.put("quantity", quantity);
                params.put("price", price);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}