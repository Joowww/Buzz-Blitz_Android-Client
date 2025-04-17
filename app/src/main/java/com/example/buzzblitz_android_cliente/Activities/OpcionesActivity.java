package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buzzblitz_android_cliente.R;

public class OpcionesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        Button btnTutorial = findViewById(R.id.btnTutorial);
        Button btnBorrarUsuario = findViewById(R.id.btnBorrarUsuario);
        Button btnBack = findViewById(R.id.btnBack);

        btnTutorial.setOnClickListener(v ->
                startActivity(new Intent(OpcionesActivity.this, TutorialActivity.class))
        );

        btnBorrarUsuario.setOnClickListener(v ->
                startActivity(new Intent(OpcionesActivity.this, BorrarUsuarioActivity.class))
        );

        btnBack.setOnClickListener(v -> finish());
    }
}