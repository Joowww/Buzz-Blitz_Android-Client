package com.example.buzzblitz_android_cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class OpcionesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        Button btnTutorial = findViewById(R.id.btnTutorial);
        btnTutorial.setOnClickListener(v -> {
            startActivity(new Intent(OpcionesActivity.this, TutorialActivity.class));
        });

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}