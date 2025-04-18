package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buzzblitz_android_cliente.R;

import java.util.Map;

public class BorrarUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrarusuario);

        Button btnSi = findViewById(R.id.btnCerrarSesion);
        Button btnNo = findViewById(R.id.btnBack);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        btnSi.setOnClickListener(v -> {
            String currentUser = sharedPreferences.getString("currentUser", "");
            if (!currentUser.isEmpty()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.remove(currentUser);
                editor.remove(currentUser + "_q1");
                editor.remove(currentUser + "_a1");

                String userId = obtenerUserIdDesdeEmail(currentUser, sharedPreferences);
                if (userId != null) editor.remove(userId + "_email");

                editor.remove("currentUser");
                editor.apply();
            }

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAffinity();
        });

        btnNo.setOnClickListener(v -> finish());
    }

    private String obtenerUserIdDesdeEmail(String email, SharedPreferences sp) {
        for (Map.Entry<String, ?> entry : sp.getAll().entrySet()) {
            if (entry.getKey().endsWith("_email") && entry.getValue().equals(email)) {
                return entry.getKey().replace("_email", "");
            }
        }
        return null;
    }
}