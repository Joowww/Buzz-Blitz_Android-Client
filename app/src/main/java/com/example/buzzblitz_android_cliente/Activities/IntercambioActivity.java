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

public class IntercambioActivity extends BaseActivity {
    private LottieAnimationView exchangeAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercambio);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
        tvUserIdCorner.setText(sharedPreferences.getString("currentUserId", ""));

        // Configurar animació Lottie
        exchangeAnim = findViewById(R.id.lottieExchange);
        exchangeAnim.setAnimation(R.raw.exchange);
        exchangeAnim.setRepeatCount(0); // Reproducció única
        exchangeAnim.setOnClickListener(v -> iniciarIntercambio());

        findViewById(R.id.btnHelp).setOnClickListener(v -> {
            startActivity(new Intent(this, HelpActivity.class));
        });
    }

    private void iniciarIntercambio() {
        exchangeAnim.setProgress(0); // Reinicia l'animació
        exchangeAnim.playAnimation();

        exchangeAnim.addAnimatorUpdateListener(animation -> {
            if (animation.getAnimatedFraction() == 1f) { // Al finalizar l'animació
                realizarIntercambioConBackend();
                exchangeAnim.removeAllUpdateListeners(); // Neteja listener
            }
        });
    }

    private void realizarIntercambioConBackend() {
        // Mock temporal
        Toast.makeText(this, "Exchange realized: 2 flowers → 1 honey jar", Toast.LENGTH_SHORT).show();
    }
}