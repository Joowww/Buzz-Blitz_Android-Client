package com.example.buzzblitz_android_cliente.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.Services.BuzzBlitzService;
import com.example.buzzblitz_android_cliente.RetrofitClient;

public class IntercambioActivity extends AppCompatActivity {
    private LottieAnimationView exchangeAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercambio);

        // Obtener y mostrar ID
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
        tvUserIdCorner.setText(sharedPreferences.getString("currentUserId", ""));

        // Configurar animación Lottie
        exchangeAnim = findViewById(R.id.lottieExchange);
        exchangeAnim.setAnimation(R.raw.exchange);
        exchangeAnim.setRepeatCount(0); // Reproducción única

        // Listener para la animación
        exchangeAnim.setOnClickListener(v -> iniciarIntercambio());

        // Botón BACK
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Botón HELP -> HelpActivity
        findViewById(R.id.btnHelp).setOnClickListener(v -> {
            startActivity(new Intent(this, HelpActivity.class));
        });
    }

    private void iniciarIntercambio() {
        exchangeAnim.setProgress(0); // Reinicia la animación
        exchangeAnim.playAnimation();

        exchangeAnim.addAnimatorUpdateListener(animation -> {
            if (animation.getAnimatedFraction() == 1f) { // Al finalizar
                realizarIntercambioConBackend();
                exchangeAnim.removeAllUpdateListeners(); // Limpiar listener
            }
        });
    }

    private void realizarIntercambioConBackend() {
        // Mock temporal
        Toast.makeText(this, "Intercambio realizado: 2 flores → 1 tarro de miel", Toast.LENGTH_SHORT).show();
    }
}