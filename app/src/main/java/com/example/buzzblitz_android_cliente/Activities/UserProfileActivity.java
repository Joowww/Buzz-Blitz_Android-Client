package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.buzzblitz_android_cliente.R;

public class UserProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileuser);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String userName = sharedPreferences.getString("currentUserName", "");

        // Mostrar nombre
        TextView tvName = findViewById(R.id.textView5);
        tvName.setText("Name: " + userName);

        // Mostrar ID en esquina (desde base_layout)
        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
        tvUserIdCorner.setText(sharedPreferences.getString("currentUserId", ""));

        // Botón BACK -> MainActivity
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish(); // Opcional: cierra esta actividad
        });
    }
}