package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.buzzblitz_android_cliente.Models.AuthUtil;
import com.example.buzzblitz_android_cliente.R;

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