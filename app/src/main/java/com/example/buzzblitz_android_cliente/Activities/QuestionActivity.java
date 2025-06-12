package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buzzblitz_android_cliente.Models.AuthUtil;
import com.example.buzzblitz_android_cliente.Models.Question;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        EditText etTitle = findViewById(R.id.etTitle);
        EditText etMessage = findViewById(R.id.etMessage);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String message = etMessage.getText().toString();
            String sender = AuthUtil.getCurrentUserId(this);
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            if(title.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Question question = new Question();
            question.setDate(date);
            question.setTitle(title);
            question.setMessage(message);
            question.setSender(sender);

            submitQuestion(question);
        });
    }

    private void submitQuestion(Question question) {
        GameBuzzBlitzService api = RetrofitClient.getApiService();
        Call<Void> call = api.submitQuestion(question);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(QuestionActivity.this, "Consulta enviada", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(QuestionActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(QuestionActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
