package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.BuzzBlitzService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrarUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrarusuario);

        Button btnSi = findViewById(R.id.btnCerrarSesion);
        Button btnNo = findViewById(R.id.btnBack);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        BuzzBlitzService apiService = RetrofitClient.getApiService();

        btnSi.setOnClickListener(v -> {
            String currentUserEmail = sharedPreferences.getString("currentUser", "");
            String userId = sharedPreferences.getString("currentUserId", ""); // Necesitamos almacenar el ID durante el login

            if (!userId.isEmpty()) {
                // Llamada a la API para borrar el usuario
                Call<Void> call = apiService.deleteUsuario(userId);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Borrar datos locales
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();

                            // Redirigir al login
                            Intent intent = new Intent(BorrarUsuarioActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(BorrarUsuarioActivity.this, "Error al borrar usuario", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(BorrarUsuarioActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Usuario no identificado", Toast.LENGTH_SHORT).show();
            }
        });

        btnNo.setOnClickListener(v -> finish());
    }
}