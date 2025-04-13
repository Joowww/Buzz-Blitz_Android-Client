package com.example.buzzblitz_android_cliente;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.security.MessageDigest;

public class RegisterBuzzBlitz extends AppCompatActivity {

    private EditText etEmailRegister, etPasswordRegister, etRepeatPassword;
    private EditText etRespuesta1, etRespuesta2, NameRegister, SurnameRegister, IdUserRegister;
    private Spinner spinnerPregunta1, spinnerPregunta2;
    private Button btnRegister, btnGoToLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        NameRegister = findViewById(R.id.NameRegister);
        SurnameRegister = findViewById(R.id.SurnameRegister);
        IdUserRegister = findViewById(R.id.IdUserRegister);
        etEmailRegister = findViewById(R.id.etEmailRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        etRepeatPassword = findViewById(R.id.etRepeatPassword);
        spinnerPregunta1 = findViewById(R.id.spinnerPregunta1);
        spinnerPregunta2 = findViewById(R.id.spinnerPregunta2);
        etRespuesta1 = findViewById(R.id.etRespuesta1);
        etRespuesta2 = findViewById(R.id.etRespuesta2);
        btnRegister = findViewById(R.id.btnRegister);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);

        sharedPreferences = getSharedPreferences("MyPreferencies", MODE_PRIVATE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.security_questions,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPregunta1.setAdapter(adapter);
        spinnerPregunta2.setAdapter(adapter);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void registrarUsuario() {
        String nombre = NameRegister.getText().toString();
        String apellido = SurnameRegister.getText().toString();
        String idUsuario = IdUserRegister.getText().toString();
        String email = etEmailRegister.getText().toString();
        String password = etPasswordRegister.getText().toString();
        String confirmPassword = etRepeatPassword.getText().toString();
        String pregunta1 = spinnerPregunta1.getSelectedItem().toString();
        String respuesta1 = etRespuesta1.getText().toString();
        String pregunta2 = spinnerPregunta2.getSelectedItem().toString();
        String respuesta2 = etRespuesta2.getText().toString();

        if(nombre.isEmpty() || apellido.isEmpty() || idUsuario.isEmpty() ||
                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Fill in all the required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if(pregunta1.equals(pregunta2)) {
            Toast.makeText(this, "Select different questions", Toast.LENGTH_SHORT).show();
            return;
        }

        if(respuesta1.isEmpty() || respuesta2.isEmpty()) {
            Toast.makeText(this, "Answer both questions", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("nombre", nombre);
        editor.putString("apellido", apellido);
        editor.putString("idUsuario", idUsuario);
        editor.putString(email, password);

        editor.putString(email + "_pregunta1", pregunta1);
        editor.putString(email + "_respuesta1", hash(respuesta1));
        editor.putString(email + "_pregunta2", pregunta2);
        editor.putString(email + "_respuesta2", hash(respuesta2));

        editor.putString(idUsuario + "_email", email);

        editor.apply();

        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}