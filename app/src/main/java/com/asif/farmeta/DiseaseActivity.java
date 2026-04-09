package com.asif.farmeta;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DiseaseActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    ImageView imageView;
    Button btnSelect, btnUpload;
    TextView tvResult;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);

        imageView = findViewById(R.id.imageView);
        btnSelect = findViewById(R.id.btnSelect);
        btnUpload = findViewById(R.id.btnUpload);
        tvResult = findViewById(R.id.tvResult);

        btnSelect.setOnClickListener(v -> openGallery());
        btnUpload.setOnClickListener(v -> {
            if(bitmap != null){
                uploadImage(bitmap);
            } else {
                Toast.makeText(this, "Select an image first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e){ e.printStackTrace(); }
        }
    }

    private void uploadImage(Bitmap bitmap){
        tvResult.setText("Detecting...");

        // 1️⃣ Bitmap to Base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        // 2️⃣ Prepare JSON
        JSONObject json = new JSONObject();
        try {
            json.put("image", encodedImage);
        } catch (Exception e){ e.printStackTrace(); }

        // 3️⃣ Prepare RequestBody
        RequestBody requestBody = RequestBody.create(
                json.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        // 4️⃣ Build Request (emulator localhost -> 10.0.2.2)
        Request request = new Request.Builder()
                .url("http://192.168.1.101/farmeta_api/plantnet_api.php") // change to your LAN IP if real device
                .post(requestBody)
                .build();

        // 5️⃣ Make network call
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> tvResult.setText("Failed: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultStr = response.body().string();
                runOnUiThread(() -> {
                    try {
                        JSONObject json = new JSONObject(resultStr);
                        String disease = json.getString("disease_name");
                        double confidence = json.getDouble("confidence");
                        String solution = json.getString("solution");

                        tvResult.setText(
                                "Disease: " + disease + "\n" +
                                        "Confidence: " + (confidence*100) + "%\n" +
                                        "Solution: " + solution
                        );
                    } catch (Exception e){
                        tvResult.setText("Error parsing result");
                        e.printStackTrace();
                    }
                });
            }
        });
    }
}