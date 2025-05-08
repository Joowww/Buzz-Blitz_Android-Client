// IntercambioActivity.java
package com.example.buzzblitz_android_cliente.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieAnimationView;
import com.example.buzzblitz_android_cliente.R;

public class IntercambioActivity extends BaseActivity {
    private LottieAnimationView exchangeAnim;
    private static final int INTERCAMBIO_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercambio);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
        tvUserIdCorner.setText(sharedPreferences.getString("currentUserId", ""));

        exchangeAnim = findViewById(R.id.lottieExchange);
        exchangeAnim.setAnimation(R.raw.exchange);
        exchangeAnim.setRepeatCount(0);
        exchangeAnim.setOnClickListener(v -> iniciarIntercambio());

        findViewById(R.id.boton_imagenhelp).setOnClickListener(v -> {
            startActivity(new Intent(this, HelpActivity.class));
        });
    }

    private void iniciarIntercambio() {
        exchangeAnim.setProgress(0);
        exchangeAnim.playAnimation();

        exchangeAnim.addAnimatorUpdateListener(animation -> {
            if (animation.getAnimatedFraction() == 1f) {
                startActivityForResult(
                        new Intent(this, CargaIntercambio.class),
                        INTERCAMBIO_REQUEST_CODE
                );
                exchangeAnim.removeAllUpdateListeners();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTERCAMBIO_REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Exchange realized: 2 flowers → 1 honey jar", Toast.LENGTH_SHORT).show();
        }
    }
}