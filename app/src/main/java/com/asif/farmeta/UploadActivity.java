package com.asif.farmeta;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    ImageView imageView;
    TextView btnCamera, btnGallery;
    Button btnUpload;
    EditText firstName, middleName, lastName, editCropName, editQuantity, editContact, editAddress, editPrice;
    Bitmap bitmap;
    private static final int CAMERA_CODE=101, GALLERY_CODE=102;

    String UPLOAD_URL = "http://192.168.1.100/farmeta_api/upload_crops.php";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imageView = findViewById(R.id.imageView);
        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGallery);
        btnUpload = findViewById(R.id.btnUpload);

        firstName = findViewById(R.id.firstName);
        middleName = findViewById(R.id.middleName);
        lastName = findViewById(R.id.lastName);
        editCropName = findViewById(R.id.editCropName);
        editQuantity = findViewById(R.id.editQuantity);
        editContact = findViewById(R.id.editContact);
        editAddress = findViewById(R.id.editAddress);
        editPrice = findViewById(R.id.editPrice);

        btnCamera.setOnClickListener(v -> {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_CODE);
            } else openCamera();
        });

        btnGallery.setOnClickListener(v -> openGallery());

        btnUpload.setOnClickListener(v -> {
            if(bitmap==null){
                Toast.makeText(this,"Select an image first",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!validateFields()) return;
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
            try{
                if(requestCode==GALLERY_CODE){
                    Uri uri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                } else if(requestCode==CAMERA_CODE && data.getExtras()!=null){
                    bitmap = (Bitmap)data.getExtras().get("data");
                }
                imageView.setImageBitmap(bitmap);
            } catch(Exception e){
                e.printStackTrace();
                Toast.makeText(this,"Image selection failed",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateFields(){
        if(firstName.getText().toString().trim().isEmpty() ||
                middleName.getText().toString().trim().isEmpty() ||
                lastName.getText().toString().trim().isEmpty() ||
                editCropName.getText().toString().trim().isEmpty() ||
                editQuantity.getText().toString().trim().isEmpty() ||
                editPrice.getText().toString().trim().isEmpty() ||
                editContact.getText().toString().trim().isEmpty() ||
                editAddress.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void uploadImage(){
        String fName = firstName.getText().toString().trim();
        String mName = middleName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String cropName = editCropName.getText().toString().trim();
        String quantity = editQuantity.getText().toString().trim();
        String price = editPrice.getText().toString().trim();
        String contact = editContact.getText().toString().trim();
        String address = editAddress.getText().toString().trim();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        String image64 = Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);

        StringRequest request = new StringRequest(Request.Method.POST, UPLOAD_URL,
                response -> Toast.makeText(this,"Upload Success",Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this,"Upload Failed: "+error.toString(),Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> map = new HashMap<>();
                map.put("first_name", fName);
                map.put("middle_name", mName);
                map.put("last_name", lName);
                map.put("crop_name", cropName);
                map.put("quantity", quantity);
                map.put("price", price);   // correct
                map.put("contact", contact);
                map.put("address", address);
                map.put("image", image64);
                return map;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}