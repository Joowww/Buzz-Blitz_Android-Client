package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.Models.AuthUtil;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        // Configurar listeners de la barra
        findViewById(R.id.nav_logout).setOnClickListener(v -> {
            startActivity(new Intent(this, CerrarSesionActivity.class));
            finishAffinity();
        });

        findViewById(R.id.nav_back).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.nav_home).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, contentFrame, true);
    }
}
