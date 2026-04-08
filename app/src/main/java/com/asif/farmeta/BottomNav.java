package com.asif.farmeta;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNav extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.home){
                    Toast.makeText(BottomNav.this, "This is home", Toast.LENGTH_SHORT).show();
                }

                else if(item.getItemId()==R.id.market){
                    Toast.makeText(BottomNav.this, "This is market", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId()==R.id.notification){
                    Toast.makeText(BottomNav.this, "This is notification", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId()==R.id.profile){
                    Toast.makeText(BottomNav.this, "This is profile", Toast.LENGTH_SHORT).show();
                }




                return true;
            }
        });

    }
}