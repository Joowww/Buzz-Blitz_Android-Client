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

        sharedPreferences = getSharedPreferences("Mypreferencies", MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = etUserIdentifier.getText().toString();
                String password = etPasswordLogin.getText().toString();

                if(validarCredenciales(userInput, password)) {
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

    private boolean validarCredenciales(String userInput, String password) {
        if(userInput.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Empty fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        String email;
        if(userInput.contains("@")) {
            email = userInput;
        } else {
            email = sharedPreferences.getString(userInput + "_email", "");
            if(email.isEmpty()) {
                Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        String savedPassword = sharedPreferences.getString(email, "");
        if(!password.equals(savedPassword)) {
            Toast.makeText(this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}