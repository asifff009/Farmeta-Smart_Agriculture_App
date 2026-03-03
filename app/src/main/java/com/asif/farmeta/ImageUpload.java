package com.asif.farmeta;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

public class ImageUpload extends AppCompatActivity {

    ImageView imageView, imageEdit;
    Button uploadButton;
    TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        imageView = findViewById(R.id.imageView);
        imageEdit = findViewById(R.id.imageEdit);
        uploadButton = findViewById(R.id.uploadButton);
        tvDisplay = findViewById(R.id.tvDisplay);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                // BitmapDrawable holo jeita chobi draw korte pare..
                Bitmap bitmap = bitmapDrawable.getBitmap();
                // ebar ei bitmap k string a convert korbo, base64 encoded string a
                // base64 encoded string paite chaile age amader byte array paite hobe
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50, outputStream);


                byte[] imageBytes = outputStream.toByteArray();
                String image64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                tvDisplay.setText(image64);

            }
        });





    }
}