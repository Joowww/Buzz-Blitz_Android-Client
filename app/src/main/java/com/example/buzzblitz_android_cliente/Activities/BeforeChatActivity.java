package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.buzzblitz_android_cliente.Models.AuthUtil;
import com.example.buzzblitz_android_cliente.R;

public class BeforeChatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beforechat);

        ImageButton btnChat = findViewById(R.id.boton_imagenchat);
        ImageButton btnForum = findViewById(R.id.boton_imagenforum);

        String userName = AuthUtil.getCurrentUserName(this);

        btnForum.setOnClickListener(v -> {
            Intent intent = new Intent(BeforeChatActivity.this, ForumActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
        });

        btnChat.setOnClickListener(v -> {
            Intent intent = new Intent(BeforeChatActivity.this, UserListActivity.class);
            intent.putExtra("CURRENT_USER_NAME", userName);
            startActivity(intent);
        });
    }
}