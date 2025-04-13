package com.example.buzzblitz_android_cliente;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        VideoView videoChat = findViewById(R.id.videoChat);
        Uri chatUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.chat);
        videoChat.setVideoURI(chatUri);
        videoChat.seekTo(1); // muestra primer frame

        videoChat.setOnClickListener(v -> {
            videoChat.start();
            videoChat.setOnCompletionListener(mp -> {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            });
        });

        VideoView videoPlay = findViewById(R.id.videoPlay);
        Uri playUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.play);
        videoPlay.setVideoURI(playUri);
        videoPlay.seekTo(1);

        videoPlay.setOnClickListener(v -> {
            videoPlay.start();
            videoPlay.setOnCompletionListener(mp -> {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
            });
        });

        VideoView videoSettings = findViewById(R.id.videoSettings);
        Uri settingsUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.settings);
        videoSettings.setVideoURI(settingsUri);
        videoSettings.seekTo(1);

        videoSettings.setOnClickListener(v -> {
            videoSettings.start();
            videoSettings.setOnCompletionListener(mp -> {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            });
        });

        VideoView videoShop = findViewById(R.id.videoShop);
        Uri shopUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.shop);
        videoShop.setVideoURI(shopUri);
        videoShop.seekTo(1);

        videoShop.setOnClickListener(v -> {
            videoShop.start();
            videoShop.setOnCompletionListener(mp -> {
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);
            });
        });
    }
}
