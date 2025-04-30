package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.Models.AuthUtil;

public class CerrarSesionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerrarsesion);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
        tvUserIdCorner.setText(sharedPreferences.getString("currentUserId", ""));

        Button btnSi = findViewById(R.id.btnCerrarSesion);
        Button btnNo = findViewById(R.id.btnBack);

        btnSi.setOnClickListener(v -> {
            // Limpiar preferencias de usuario
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("currentUser");
            editor.remove("currentUserId");
            editor.remove("currentUserName");
            editor.remove("showWelcome");
            editor.apply();

            // Actualizar estado de sesión
            AuthUtil.setUserLoggedIn(CerrarSesionActivity.this, false);

            // Redirigir al login y limpiar pila de actividades
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finishAffinity();
        });

        btnNo.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}