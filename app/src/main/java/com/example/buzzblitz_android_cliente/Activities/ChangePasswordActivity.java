package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buzzblitz_android_cliente.Models.Usulogin;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        EditText etUserId = findViewById(R.id.etUserId);
        EditText etNuevaContra = findViewById(R.id.etNuevaContra);
        Button btnCambiar = findViewById(R.id.btnCambiar);

        btnCambiar.setOnClickListener(v -> {
            String userId = etUserId.getText().toString();
            String nuevaContra = etNuevaContra.getText().toString();

            if (validarDatos(userId, nuevaContra)) {
                Usulogin datos = new Usulogin(userId, nuevaContra);
                GameBuzzBlitzService api = RetrofitClient.getApiService();
                api.cambiarContraseña(datos).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ChangePasswordActivity.this, "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            manejarErrores(response);
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ChangePasswordActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean validarDatos(String userId, String nuevaContra) {
        if (userId.isEmpty()) {
            Toast.makeText(this, "El ID de usuario no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (nuevaContra.isEmpty()) {
            Toast.makeText(this, "La nueva contraseña no puede estar vacía", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (nuevaContra.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void manejarErrores(Response<?> response) {
        if (response.code() == 401) {
            Toast.makeText(this, "Credenciales incorrectas. Verifica tu ID de usuario.", Toast.LENGTH_SHORT).show();
        } else if (response.code() == 500) {
            Toast.makeText(this, "Error interno del servidor. Intenta más tarde.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error desconocido: " + response.code(), Toast.LENGTH_SHORT).show();
        }
    }
}