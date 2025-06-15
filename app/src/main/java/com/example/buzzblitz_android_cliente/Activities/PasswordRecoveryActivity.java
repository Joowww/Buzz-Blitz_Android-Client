package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buzzblitz_android_cliente.Models.OlvContra;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordRecoveryActivity extends BaseActivity {

    private static final String TAG = "PASSWORD_RECOVERY_LOG";
    private EditText etUserId, etRespuesta;
    private Button btnGetQuestion, btnRecuperar;
    private TextView tvQuestion, tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        etUserId = findViewById(R.id.etUserId);
        etRespuesta = findViewById(R.id.etRespuesta);
        btnGetQuestion = findViewById(R.id.btnGetQuestion);
        btnRecuperar = findViewById(R.id.btnRecuperar);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvResultado = findViewById(R.id.tvResultado);

        btnGetQuestion.setOnClickListener(v -> {
            String userId = etUserId.getText().toString().trim();
            Log.d(TAG, "Solicitando pregunta de seguridad para userId: " + userId);
            if (userId.isEmpty()) {
                Toast.makeText(this, "Enter your user ID", Toast.LENGTH_SHORT).show();
                return;
            }

            GameBuzzBlitzService api = RetrofitClient.getApiService();
            api.obtenerPreguntaSeguridad(userId).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d(TAG, "Respuesta obtenerPreguntaSeguridad: code=" + response.code() + ", body=" + response.body());
                    if (response.isSuccessful() && response.body() != null) {
                        tvQuestion.setText(response.body());
                        tvQuestion.setVisibility(View.VISIBLE);
                        etRespuesta.setVisibility(View.VISIBLE);
                        btnRecuperar.setVisibility(View.VISIBLE);
                        tvResultado.setText(""); // Limpiar resultado anterior
                    } else {
                        tvResultado.setText("Error: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e(TAG, "Error red obtenerPreguntaSeguridad: " + t.getMessage());
                    tvResultado.setText("Network error: " + t.getMessage());
                }
            });
        });

        btnRecuperar.setOnClickListener(v -> {
            String respuesta = etRespuesta.getText().toString().trim();
            String userId = etUserId.getText().toString().trim();
            Log.d(TAG, "Intentando recuperar cuenta para userId: " + userId + ", respuesta: " + respuesta);
            if (respuesta.isEmpty()) {
                Toast.makeText(this, "Enter security answer", Toast.LENGTH_SHORT).show();
                return;
            }

            OlvContra datos = new OlvContra(userId, respuesta);
            GameBuzzBlitzService api = RetrofitClient.getApiService();
            api.recuperarCuenta(datos).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d(TAG, "Respuesta recuperarCuenta: code=" + response.code() + ", body=" + response.body());
                    if (response.isSuccessful() && response.body() != null) {
                        tvResultado.setText("Tu contraseña es: " + response.body());
                    } else {
                        tvResultado.setText("Error: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e(TAG, "Error red recuperarCuenta: " + t.getMessage());
                    tvResultado.setText("Network error: " + t.getMessage());
                }
            });
        });
    }
}