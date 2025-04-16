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
            // Configurar animación (versión Lottie 5.2.0)
            LottieAnimationView animationView = findViewById(R.id.animationView);
            animationView.setAnimation(R.raw.abejavolando); // Usar recurso RAW
            animationView.loop(false); // Reproducir solo una vez
            animationView.playAnimation();

            // Redirigir al login después de 3 segundos
            new Handler().postDelayed(() -> {
                startActivity(new Intent(CargarJuegoActivity.this, LoginBuzzBlitz.class));
                finish();
            }, 3000);

        } catch (Exception e) {
            Log.e("CargarJuegoError", "Error: " + e.getMessage());
            startActivity(new Intent(this, LoginBuzzBlitz.class)); // Redirigir si falla
            finish();
        }
    }
}