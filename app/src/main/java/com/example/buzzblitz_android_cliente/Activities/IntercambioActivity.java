package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.Models.Intercambio;
import com.example.buzzblitz_android_cliente.Models.AuthUtil;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;
import com.example.buzzblitz_android_cliente.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntercambioActivity extends BaseActivity {
    private static final int REQUEST_CARGA_INTERCAMBIO = 1001;
    private LottieAnimationView exchangeAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercambio);

        SharedPreferences sharedPreferences = getSharedPreferences(AuthUtil.PREFS_NAME, MODE_PRIVATE);
        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
        tvUserIdCorner.setText(AuthUtil.getCurrentUserId(this));

        exchangeAnim = findViewById(R.id.lottieExchange);
        exchangeAnim.setAnimation(R.raw.exchange);
        exchangeAnim.setRepeatCount(0);
        exchangeAnim.setOnClickListener(v -> iniciarCargaIntercambio());

        findViewById(R.id.boton_imagenhelp).setOnClickListener(v -> {
            startActivity(new Intent(this, HelpActivity.class));
        });
    }

    private void iniciarCargaIntercambio() {
        Intent intent = new Intent(this, CargaIntercambioActivity.class);
        startActivityForResult(intent, REQUEST_CARGA_INTERCAMBIO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CARGA_INTERCAMBIO && resultCode == RESULT_OK) {
            realizarIntercambioEnServidor();
        }
    }

    private void realizarIntercambioEnServidor() {
        String usuarioId = AuthUtil.getCurrentUserId(this);

        GameBuzzBlitzService api = RetrofitClient.getApiService();
        Call<Intercambio> call = api.intercambiarFlores(usuarioId);
        call.enqueue(new Callback<Intercambio>() {
            @Override
            public void onResponse(Call<Intercambio> call, Response<Intercambio> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int nuevosTarros = response.body().getTarrosMiel();

                    AuthUtil.setCurrentTarrosMiel(IntercambioActivity.this, nuevosTarros);
                    AuthUtil.setCurrentFlor(IntercambioActivity.this, 0);
                    AuthUtil.setCurrentFloreGold(IntercambioActivity.this, 0);

                    Toast.makeText(IntercambioActivity.this,
                            "¡Exchange realized! Honey jars: " + nuevosTarros,
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(IntercambioActivity.this, "Exchange failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Intercambio> call, Throwable t) {
                Toast.makeText(IntercambioActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}