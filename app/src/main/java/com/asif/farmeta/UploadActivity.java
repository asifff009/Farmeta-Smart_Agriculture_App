package com.asif.farmeta;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnCamera, btnGallery, btnUpload;
    EditText editName;
    Bitmap bitmap;
    private static final int CAMERA_CODE=101, GALLERY_CODE=102;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imageView = findViewById(R.id.imageView);
        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGallery);
        btnUpload = findViewById(R.id.btnUpload);
        editName = findViewById(R.id.editName);

        btnCamera.setOnClickListener(v -> {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_CODE);
            }else openCamera();
        });

        btnGallery.setOnClickListener(v -> openGallery());

        btnUpload.setOnClickListener(v -> {
            if(bitmap==null){
                Toast.makeText(this,"Select an image first",Toast.LENGTH_SHORT).show();
                return;
            }
            uploadImage();
        });
    }

    private void openCamera(){
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera,CAMERA_CODE);
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery,GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode== Activity.RESULT_OK && data!=null){
            if(requestCode==GALLERY_CODE){
                Uri uri = data.getData();
                try{ bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri); }
                catch(Exception e){ e.printStackTrace(); }
                imageView.setImageBitmap(bitmap);
            }else if(requestCode==CAMERA_CODE){
                bitmap = (Bitmap)data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private void uploadImage(){
        String name = editName.getText().toString();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        String image64 = Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);

        String url = "http://192.168.1.100/farmeta_api/upload_crops.php";

        StringRequest request = new StringRequest(Request.Method.POST,url,
                response -> Toast.makeText(this,"Upload Success",Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this,error.toString(),Toast.LENGTH_SHORT).show()){
            protected Map<String,String> getParams(){
                Map<String,String> map = new HashMap<>();
                map.put("name",name);
                map.put("image",image64);
                return map;
            }
        };
        Toast.makeText(this,"Upload Success",Toast.LENGTH_SHORT).show();
        Volley.newRequestQueue(this).add(request);
    }
}