package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.widget.Toast;
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

        // Configurar animación Lottie
        exchangeAnim = findViewById(R.id.lottieExchange);
        exchangeAnim.setAnimation(R.raw.exchange);
        exchangeAnim.setRepeatCount(0); // Solo se reproduce una vez

        // Listener para la animación
        exchangeAnim.setOnClickListener(v -> iniciarIntercambio());

        // Botón BACK
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void iniciarIntercambio() {
        exchangeAnim.playAnimation();
        exchangeAnim.addAnimatorUpdateListener(animation -> {
            if (animation.getAnimatedFraction() == 1f) { // Cuando la animación termina
                realizarIntercambioConBackend();
            }
        });
    }

    private void realizarIntercambioConBackend() {
        BuzzBlitzService api = RetrofitClient.getApiService();
        // Ejemplo: Llamada a tu endpoint de intercambio (¡implementa tu lógica!)
        /* Call<RespuestaIntercambio> call = api.intercambiarFlores();
        call.enqueue(new Callback<RespuestaIntercambio>() {
            @Override
            public void onResponse(Call<RespuestaIntercambio> call, Response<RespuestaIntercambio> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(this, "¡Intercambio exitoso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error en el intercambio", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaIntercambio> call, Throwable t) {
                Toast.makeText(this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });*/

        // Mock temporal (elimina esto cuando implementes la API)
        Toast.makeText(this, "Intercambio realizado: 2 flores → 1 tarro de miel", Toast.LENGTH_SHORT).show();
    }
}