package com.example.buzzblitz_android_cliente;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class CargarJuegoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargarjuego);

        try {
            LottieAnimationView animationBee = findViewById(R.id.animationView);
            animationBee.setAnimation(R.raw.abejavolando);
            animationBee.loop(false);
            animationBee.playAnimation();

            LottieAnimationView animationMushroom = findViewById(R.id.mushroomView);
            animationMushroom.setAnimation(R.raw.mushroom);
            animationMushroom.setSpeed(2.5f);
            animationMushroom.loop(true);
            animationMushroom.playAnimation();

            new Handler().postDelayed(() -> {
                startActivity(new Intent(CargarJuegoActivity.this, LoginBuzzBlitz.class));
                finish();
            }, 3000);

        } catch (Exception e) {
            Log.e("CargarJuegoError", "Error: " + e.getMessage());
            startActivity(new Intent(this, LoginBuzzBlitz.class));
            finish();
        }
    }
}