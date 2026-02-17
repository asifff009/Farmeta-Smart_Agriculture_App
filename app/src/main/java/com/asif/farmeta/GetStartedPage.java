package com.asif.farmeta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GetStartedPage extends AppCompatActivity {

    LinearLayout getStartedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_page);

        getStartedBtn = findViewById(R.id.getStartedBtn);

        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetStartedPage.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}