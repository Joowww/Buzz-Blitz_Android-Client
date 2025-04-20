package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buzzblitz_android_cliente.R;

public class ChatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Botón BACK -> BeforeChatActivity
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            startActivity(new Intent(this, BeforeChatActivity.class));
            finish(); // Opcional: cierra esta actividad
        });
    }
}