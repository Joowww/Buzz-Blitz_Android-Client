package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.buzzblitz_android_cliente.R;

public class UserProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileuser);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String userName = sharedPreferences.getString("currentUserName", "");

        TextView tvName = findViewById(R.id.textView5);
        tvName.setText("Name: " + userName);

        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
        tvUserIdCorner.setText(sharedPreferences.getString("currentUserId", ""));

        Button btnInventory = findViewById(R.id.btnGoToInventory);

        btnInventory.setOnClickListener(v ->
                startActivity(new Intent(UserProfileActivity.this, InventoryActivity.class))
        );


    }
}