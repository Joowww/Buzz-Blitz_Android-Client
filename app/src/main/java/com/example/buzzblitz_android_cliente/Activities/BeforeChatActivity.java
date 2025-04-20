package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buzzblitz_android_cliente.R;

public class BeforeChatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beforechat);

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