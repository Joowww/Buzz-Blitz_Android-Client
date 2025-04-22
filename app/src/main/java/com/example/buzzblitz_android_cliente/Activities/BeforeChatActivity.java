package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.buzzblitz_android_cliente.R;

public class BeforeChatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beforechat);

        // Obtener y mostrar ID
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
        tvUserIdCorner.setText(sharedPreferences.getString("currentUserId", ""));

        // Botón BACK -> MainActivity
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        // Botón CHAT -> ChatActivity
        findViewById(R.id.btnChat).setOnClickListener(v ->
                startActivity(new Intent(this, ChatActivity.class))
        );

        // Botón FORUM -> ForumActivity
        findViewById(R.id.btnForum).setOnClickListener(v ->
                startActivity(new Intent(this, ForumActivity.class))
        );
    }
}