package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buzzblitz_android_cliente.Models.Usulogin;
import com.example.buzzblitz_android_cliente.Models.AuthUtil;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends BaseActivity {
    private static final String TAG = "CHANGE_PASSWORD_LOG";
    private boolean isProcessing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        EditText etUserId = findViewById(R.id.etUserId);
        EditText etNuevaContra = findViewById(R.id.etNuevaContra);
        Button btnCambiar = findViewById(R.id.btnCambiar);

        btnCambiar.setOnClickListener(v -> {
            if (isProcessing) return;

            isProcessing = true;
            String userId = etUserId.getText().toString();
            String nuevaContra = etNuevaContra.getText().toString();

            Log.d(TAG, "Trying to change the password for userId: " + userId);

            if (validarDatos(userId, nuevaContra)) {
                Usulogin datos = new Usulogin(userId, nuevaContra);
                GameBuzzBlitzService api = RetrofitClient.getApiService();

                api.cambiarContraseña(datos).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        isProcessing = false;
                        if (response.isSuccessful()) {
                            AuthUtil.setUserLoggedIn(ChangePasswordActivity.this, false);

                            Toast.makeText(ChangePasswordActivity.this,
                                    "Password changed. Please Log in again",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            manejarErrores(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        isProcessing = false;
                        Log.e(TAG, "Network error change password: " + t.getMessage());
                        Toast.makeText(ChangePasswordActivity.this,
                                "Network error: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                isProcessing = false;
            }
        });
    }

    private boolean validarDatos(String userId, String nuevaContra) {
        if (userId.isEmpty()) {
            Toast.makeText(this, "User ID cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (nuevaContra.isEmpty()) {
            Toast.makeText(this, "New password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (nuevaContra.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void manejarErrores(Response<?> response) {
        if (response.code() == 401) {
            Toast.makeText(this, "Incorrect credentials. Please check your user ID.", Toast.LENGTH_SHORT).show();
        } else if (response.code() == 500) {
            Toast.makeText(this, "Internal server error. Please try again later.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unknown error: " + response.code(), Toast.LENGTH_SHORT).show();
        }
    }
}