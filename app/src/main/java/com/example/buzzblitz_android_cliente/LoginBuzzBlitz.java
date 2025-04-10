package com.example.buzzblitz_android_cliente;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginBuzzBlitz extends AppCompatActivity {

    private EditText etEmailLogin, etPasswordLogin;
    private Button btnLogin, btnGoToRegister;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);

        sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmailLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();

                if(validarCredenciales(email, password)) {
                    startActivity(new Intent(LoginBuzzBlitz.this, MainActivity.class));
                    finish();
                }
            }
        });

        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginBuzzBlitz.this, RegisterBuzzBlitz.class));
            }
        });
    }

    private boolean validarCredenciales(String email, String password) {
        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Campos vacíos", Toast.LENGTH_SHORT).show();
            return false;
        }

        String savedPassword = sharedPreferences.getString(email, "");
        if(!password.equals(savedPassword)) {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}