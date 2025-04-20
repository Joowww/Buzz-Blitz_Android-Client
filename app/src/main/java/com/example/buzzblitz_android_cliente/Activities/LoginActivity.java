package com.example.buzzblitz_android_cliente.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buzzblitz_android_cliente.Models.Usulogin;
import com.example.buzzblitz_android_cliente.Models.Usuario;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.BuzzBlitzService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserIdentifier, etPasswordLogin;
    private Button btnLogin, btnGoToRegister;
    private SharedPreferences sharedPreferences;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserIdentifier = findViewById(R.id.etUserIdentifier);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        // Configurar listeners dentro de onCreate
        btnLogin.setOnClickListener(this::enviarLogin); // Usar método como referencia
        btnGoToRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );
    }

    public void enviarLogin(View view) { // Asegurar que es público y recibe View
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

        // Configurar Retrofit con URL correcta (incluir dsaApp/)
//        BuzzBlitzService api = RetrofitClient.getClient("http://10.0.2.2:8080/dsaApp/")
//                .create(BuzzBlitzService.class);

        BuzzBlitzService api = RetrofitClient.getApiService();

        // Hacer la llamada a la API
        Call<Usuario> call = api.loginUsuario(peticion);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Log.d("LOGIN_DEBUG", "Código HTTP: " + response.code());
                i=1;
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    Log.d("LOGIN_DEBUG", "Login exitoso. Usuario: " + usuario.getMail());

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("currentUser", usuario.getMail());
                    editor.apply();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    i=2;
                    finish();
                } else {
                    i=3;
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("LOGIN_DEBUG", "Error del servidor: " + errorBody);
                        } catch (IOException e) {
                            Log.e("LOGIN_DEBUG", "Error al leer el cuerpo del error", e);
                        }
                    }
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                i=4;
                Log.e("LOGIN_DEBUG", "Error de red: ", t);
                Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }); // Cierre correcto del callback
    }
}