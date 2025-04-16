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
            LottieAnimationView animationView = findViewById(R.id.animationView);
            animationView.setAnimation(R.raw.abejavolando);
            animationView.loop(false);
            animationView.playAnimation();

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