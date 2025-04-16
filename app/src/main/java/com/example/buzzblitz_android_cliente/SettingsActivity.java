package com.example.buzzblitz_android_cliente;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        AppCompatButton btnCredits = findViewById(R.id.btnCredits);
        btnCredits.setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, CreditsActivity.class));
        });

        AppCompatButton btnPolitica = findViewById(R.id.btnPoliticaPriv);
        btnPolitica.setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, PoliticaPrivacidadActivity.class));
        });

        AppCompatButton btnTerminos = findViewById(R.id.btnTerminosCondiciones);
        btnTerminos.setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, TerminosActivity.class));
        });

        AppCompatButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}
