package com.example.buzzblitz_android_cliente;

import androidx.appcompat.app.AppCompatActivity;
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

        // Initialize UI components
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

        // Set up SharedPreferences
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        // Configure security questions spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.security_questions,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPregunta1.setAdapter(adapter);
        spinnerPregunta2.setAdapter(adapter);

        // Register button click handler
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Login navigation button
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void registerUser() {
        String firstName = NameRegister.getText().toString().trim();
        String lastName = SurnameRegister.getText().toString().trim();
        String userId = IdUserRegister.getText().toString().trim();
        String email = etEmailRegister.getText().toString().trim();
        String password = etPasswordRegister.getText().toString().trim();
        String confirmPassword = etRepeatPassword.getText().toString().trim();
        String question1 = spinnerPregunta1.getSelectedItem().toString();
        String answer1 = etRespuesta1.getText().toString().trim();
        String question2 = spinnerPregunta2.getSelectedItem().toString();
        String answer2 = etRespuesta2.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || userId.isEmpty() ||
                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (question1.equals(question2)) {
            Toast.makeText(this, "Please select different security questions", Toast.LENGTH_SHORT).show();
            return;
        }

        if (answer1.isEmpty() || answer2.isEmpty()) {
            Toast.makeText(this, "Please answer both security questions", Toast.LENGTH_SHORT).show();
            return;
        }

        if (sharedPreferences.contains(email)) {
            Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show();
            return;
        }

        if (sharedPreferences.contains(userId + "_email")) {
            Toast.makeText(this, "User ID already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(email, password);
        editor.putString(userId + "_email", email);

        editor.putString(email + "_q1", question1);
        editor.putString(email + "_a1", hash(answer1));
        editor.putString(email + "_q2", question2);
        editor.putString(email + "_a2", hash(answer2));

        editor.commit();

        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Hashing failed", ex);
        }
    }
}