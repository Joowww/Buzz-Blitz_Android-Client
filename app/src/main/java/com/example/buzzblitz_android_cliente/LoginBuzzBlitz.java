package com.example.buzzblitz_android_cliente;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buzzblitz_android_cliente.Activities.MainActivity;
import com.example.buzzblitz_android_cliente.Models.Usulogin;
import com.example.buzzblitz_android_cliente.Models.Usuario;
import com.example.buzzblitz_android_cliente.Services.BuzzBlitzService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginBuzzBlitz extends AppCompatActivity {

    private EditText etUserIdentifier, etPasswordLogin;
    private Button btnLogin, btnGoToRegister;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserIdentifier = findViewById(R.id.etUserIdentifier);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {
            String userInput = etUserIdentifier.getText().toString().trim();
            String password = etPasswordLogin.getText().toString().trim();

            if (userInput.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Empty fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear objeto de login
            Usulogin peticion = new Usulogin();
            peticion.setIdoname(userInput);
            peticion.setPswd(password);

            // Configurar Retrofit
            BuzzBlitzService api = RetrofitClient.getClient("http://10.0.2.2:8080/")
                    .create(BuzzBlitzService.class);

            // Hacer la llamada a la API
            Call<Usuario> call = api.loginUsuario(peticion);
            // De forma asíncrona esperando la respuesta del servidor
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    // Log del código de respuesta HTTP
                    Log.d("LOGIN_DEBUG", "Código HTTP: " + response.code());

                    if (response.isSuccessful() && response.body() != null) {
                        // === LÍNEAS AÑADIDAS ===
                        Usuario usuario = response.body();
                        Log.d("LOGIN_DEBUG", "Login exitoso. Usuario: " + usuario.getMail());

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("currentUser", usuario.getMail());
                        editor.apply();

                        startActivity(new Intent(LoginBuzzBlitz.this, MainActivity.class));
                        finish();
                        // ========================
                    }
                    else {
                        if (response.errorBody() != null) {
                            try {
                                String errorBody = response.errorBody().string();
                                Log.e("LOGIN_DEBUG", "Error del servidor: " + errorBody);
                            }
                            catch (IOException e) {
                                Log.e("LOGIN_DEBUG", "Error al leer el cuerpo del error", e);
                            }
                        }
                        Log.e("LOGIN_DEBUG", "Respuesta no exitosa o cuerpo vacío");
                        Toast.makeText(LoginBuzzBlitz.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Log.e("LOGIN_DEBUG", "Error de red: ", t);
                    Toast.makeText(LoginBuzzBlitz.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnGoToRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginBuzzBlitz.this, RegisterBuzzBlitz.class))
        );
    }
}