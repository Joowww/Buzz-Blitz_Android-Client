package com.example.buzzblitz_android_cliente.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buzzblitz_android_cliente.Models.AuthUtil;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends BaseActivity {

    private TextView tvName, tvEmail, tvTotalHoney, tvGoldenFlowers, tvNormalFlowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileuser);

        tvName = findViewById(R.id.textView5);
        tvEmail = findViewById(R.id.textView7);
        tvTotalHoney = findViewById(R.id.tvTotalHoney);
        tvGoldenFlowers = findViewById(R.id.tvGoldenFlowers);
        tvNormalFlowers = findViewById(R.id.tvNormalFlowers);

        cargarDatosUsuario();

        // Obtener datos usando AuthUtil
        String userName = AuthUtil.getCurrentUserName(this);
        String userEmail = AuthUtil.getCurrentUserEmail(this);

        tvName.setText("Name: " + userName);
        tvEmail.setText("Email: " + userEmail);

        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
        tvUserIdCorner.setText(AuthUtil.getCurrentUserId(this));

        Button btnInventory = findViewById(R.id.btnGoToInventory);
        btnInventory.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, InventoryActivity.class);
            startActivity(intent);
        });

        Button btnBadges = findViewById(R.id.btnBadges);
        btnBadges.setOnClickListener(v -> {
            startActivity(new Intent(UserProfileActivity.this, BadgesActivity.class));
        });

        Button btnCambiarPswd = findViewById(R.id.btnCambiarPswd);
        btnCambiarPswd.setOnClickListener(v -> {
            startActivity(new Intent(this, ChangePasswordActivity.class));
        });

        Button btnResetData = findViewById(R.id.btnResetData);
        btnResetData.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Reset Confirm")
                    .setMessage("Are you sure you want to reset all your data?")
                    .setPositiveButton("Yes", (dialog, which) -> resetUserData())
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
    private void resetUserData() {
        int tarrosMiel = AuthUtil.getCurrentTarrosMiel(this);
        int flor = AuthUtil.getCurrentFlor(this);
        int floreGold = AuthUtil.getCurrentFloreGold(this);

        if (tarrosMiel == 0 && flor == 0 && floreGold == 0) {
            Toast.makeText(this, "You already have 0, which is the minimum. You cannot reset.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = AuthUtil.getCurrentUserId(this);
        GameBuzzBlitzService api = RetrofitClient.getApiService();

        api.resetUserData(userId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    AuthUtil.setCurrentTarrosMiel(UserProfileActivity.this, 0);
                    AuthUtil.setCurrentFlor(UserProfileActivity.this, 0);
                    AuthUtil.setCurrentFloreGold(UserProfileActivity.this, 0);
                    AuthUtil.setCurrentBestScore(UserProfileActivity.this, 0);
                    AuthUtil.setCurrentNumPartidas(UserProfileActivity.this, 0);

                    onResume();

                    Toast.makeText(UserProfileActivity.this,
                            "Data reset successfully",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserProfileActivity.this,
                            "Error resetting data: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosUsuario();
    }
    private void cargarDatosUsuario() {
        String userName = AuthUtil.getCurrentUserName(this);
        String userEmail = AuthUtil.getCurrentUserEmail(this);
        int tarrosMiel = AuthUtil.getCurrentTarrosMiel(this);
        int flor = AuthUtil.getCurrentFlor(this);
        int floreGold = AuthUtil.getCurrentFloreGold(this);

        tvName.setText("Name: " + (userName != null ? userName : ""));
        tvEmail.setText("Email: " + (userEmail != null ? userEmail : ""));
        tvTotalHoney.setText("Total honey jars: " + tarrosMiel);
        tvNormalFlowers.setText("Normal flowers: " + flor);
        tvGoldenFlowers.setText("Golden flowers: " + floreGold);
    }
}