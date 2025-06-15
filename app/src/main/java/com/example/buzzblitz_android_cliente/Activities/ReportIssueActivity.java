package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buzzblitz_android_cliente.Models.AuthUtil;
import com.example.buzzblitz_android_cliente.Models.Issue;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportIssueActivity extends BaseActivity {
    private EditText etTitle, etMessage;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);

        etTitle = findViewById(R.id.etTitle);
        etMessage = findViewById(R.id.etMessage);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String message = etMessage.getText().toString();
            String sender = AuthUtil.getCurrentUserId(this); // Usuario actual

            Issue issue = new Issue(title, message, sender);
            reportIssue(issue);
        });
    }

    private void reportIssue(Issue issue) {
        GameBuzzBlitzService api = RetrofitClient.getApiService();
        Call<Void> call = api.reportIssue(issue);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ReportIssueActivity.this, "Report submitted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ReportIssueActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
