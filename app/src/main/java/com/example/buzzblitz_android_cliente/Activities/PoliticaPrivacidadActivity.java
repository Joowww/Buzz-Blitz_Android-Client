package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.buzzblitz_android_cliente.R;

public class PoliticaPrivacidadActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politica);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
        tvUserIdCorner.setText(sharedPreferences.getString("currentUserId", ""));

        AppCompatButton btnListIssues = findViewById(R.id.btnListIssues);
        btnListIssues.setOnClickListener(v -> {
            startActivity(new Intent(PoliticaPrivacidadActivity.this, IssueListActivity.class));
        });

        AppCompatButton btnReportIssue = findViewById(R.id.btnReportIssue);
        btnReportIssue.setOnClickListener(v -> {
            startActivity(new Intent(PoliticaPrivacidadActivity.this, ReportIssueActivity.class));
        });
    }
}
