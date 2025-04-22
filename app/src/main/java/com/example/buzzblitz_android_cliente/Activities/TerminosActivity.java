package com.example.buzzblitz_android_cliente.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.buzzblitz_android_cliente.R;

public class TerminosActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos);

        // Obtener y mostrar ID
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
        tvUserIdCorner.setText(sharedPreferences.getString("currentUserId", ""));

        AppCompatButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}
