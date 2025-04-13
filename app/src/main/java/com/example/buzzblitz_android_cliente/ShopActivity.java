package com.example.buzzblitz_android_cliente;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        VideoView videoMoney = findViewById(R.id.videoMoneyExchange);
        Uri moneyUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.moneyexchange);
        videoMoney.setVideoURI(moneyUri);
        videoMoney.seekTo(1);

        videoMoney.setOnClickListener(v -> {
            videoMoney.start();
            videoMoney.setOnCompletionListener(mp -> {
                Intent intent = new Intent(ShopActivity.this, MoneyExchangeActivity.class);
                startActivity(intent);
            });
        });

        VideoView videoComprar = findViewById(R.id.videoComprar);
        Uri comprarUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.comprar);
        videoComprar.setVideoURI(comprarUri);
        videoComprar.seekTo(1);

        videoComprar.setOnClickListener(v -> {
            videoComprar.start();
            videoComprar.setOnCompletionListener(mp -> {
                Intent intent = new Intent(ShopActivity.this, ComprarActivity.class);
                startActivity(intent);
            });
        });
    }
}

