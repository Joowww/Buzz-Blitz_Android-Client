package com.example.buzzblitz_android_cliente.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Adapters.MyRankingAdapter;
import com.example.buzzblitz_android_cliente.Models.Info;
import com.example.buzzblitz_android_cliente.Models.InfoList;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.BuzzBlitzService;

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

        tvBestScore = findViewById(R.id.textView6);
        tvBestScore.setText("Mejor puntuación: " + bestScore);

        rvRanking = findViewById(R.id.rvRanking);
        rvRanking.setLayoutManager(new LinearLayoutManager(this));

        BuzzBlitzService api = RetrofitClient.getApiService();
        api.getInfo().enqueue(new Callback<InfoList>() {
            @Override
            public void onResponse(Call<InfoList> call, Response<InfoList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Info> rankingFromApi = response.body().getRanking();
                    MyRankingAdapter adapter = new MyRankingAdapter(rankingFromApi, userId);
                    rvRanking.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<InfoList> call, Throwable t) {
                Toast.makeText(RankingActivity.this, "Error al cargar el ranking", Toast.LENGTH_SHORT).show();
            }
        });
    }
}