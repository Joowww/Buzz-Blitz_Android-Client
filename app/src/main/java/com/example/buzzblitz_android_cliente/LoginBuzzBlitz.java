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

            if (validarCredenciales(userInput, password)) {
                startActivity(new Intent(LoginBuzzBlitz.this, MainActivity.class));
                finish();
            }
        });

        btnGoToRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginBuzzBlitz.this, RegisterBuzzBlitz.class))
        );
    }

    private boolean validarCredenciales(String userInput, String password) {
        if (userInput.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Empty fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        String email;
        if (userInput.contains("@")) {
            email = userInput;
        } else {
            email = sharedPreferences.getString(userInput + "_email", "");
            if (email.isEmpty()) {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (!sharedPreferences.contains(email)) {
            Toast.makeText(this, "Unregistered account", Toast.LENGTH_SHORT).show();
            return false;
        }

        String savedPassword = sharedPreferences.getString(email, "");
        if (!password.equals(savedPassword)) {
            Toast.makeText(this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
            return false;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentUser", email);
        editor.apply();

        return true;
    }
}