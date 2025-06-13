package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buzzblitz_android_cliente.Models.OlvContra;
import com.example.buzzblitz_android_cliente.Models.Usuario;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordRecoveryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        EditText etUserId = findViewById(R.id.etUserId);
        EditText etRespuesta = findViewById(R.id.etRespuesta);
        Button btnRecuperar = findViewById(R.id.btnRecuperar);
        TextView tvResultado = findViewById(R.id.tvResultado);

        btnRecuperar.setOnClickListener(v -> {
            String userId = etUserId.getText().toString();
            String respuesta = etRespuesta.getText().toString();

            if (validarDatos(userId, respuesta)) {
                OlvContra datos = new OlvContra(userId, respuesta);
                GameBuzzBlitzService api = RetrofitClient.getApiService();
                api.recuperarCuenta(datos).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            tvResultado.setText("Tu contraseña es: " + response.body().getPswd());
                        } else {
                            manejarErrores(response, tvResultado);
                        }
                    }
                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(PasswordRecoveryActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean validarDatos(String userId, String respuesta) {
        if (userId.isEmpty()) {
            Toast.makeText(this, "El ID de usuario no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (respuesta.isEmpty()) {
            Toast.makeText(this, "La respuesta de seguridad no puede estar vacía", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void manejarErrores(Response<?> response, TextView tvResultado) {
        if (response.code() == 401) {
            tvResultado.setText("Credenciales incorrectas. Verifica tu respuesta de seguridad.");
        } else if (response.code() == 500) {
            tvResultado.setText("Error interno del servidor. Intenta más tarde.");
        } else {
            tvResultado.setText("Error desconocido: " + response.code());
        }
    }
}