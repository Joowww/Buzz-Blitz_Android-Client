package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Adapters.MyRankingAdapter;
import com.example.buzzblitz_android_cliente.Models.Info;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.BuzzBlitzService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingActivity extends BaseActivity {
    private RecyclerView rvRanking;
    private TextView tvBestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        SharedPreferences prefs = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String userId = prefs.getString("currentUserId", "");
        int bestScore = prefs.getInt("currentBestScore", 0);

        // Configurar título
        tvBestScore = findViewById(R.id.textView6);
        tvBestScore.setText("Best player score: " + bestScore);

        // Configurar RecyclerView
        rvRanking = findViewById(R.id.rvRanking);
        rvRanking.setLayoutManager(new LinearLayoutManager(this));

        // Obtener datos del ranking
        BuzzBlitzService api = RetrofitClient.getApiService();
        api.getClasificacion().enqueue(new Callback<List<Info>>() {
            @Override
            public void onResponse(Call<List<Info>> call, Response<List<Info>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Info> sortedList = new ArrayList<>(response.body());
                    Collections.sort(sortedList, (o1, o2) ->
                            Integer.compare(o2.getMejorPuntuacion(), o1.getMejorPuntuacion())
                    );

                    MyRankingAdapter adapter = new MyRankingAdapter(sortedList, userId);
                    rvRanking.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Info>> call, Throwable t) {
                Toast.makeText(RankingActivity.this, "Error al cargar el ranking", Toast.LENGTH_SHORT).show();
            }
        });
    }
}