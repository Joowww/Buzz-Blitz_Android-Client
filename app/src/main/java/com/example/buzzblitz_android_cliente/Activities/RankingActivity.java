package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Adapters.MyRankingAdapter;
import com.example.buzzblitz_android_cliente.Models.AuthUtil;
import com.example.buzzblitz_android_cliente.Models.Info;
import com.example.buzzblitz_android_cliente.Models.InfoList;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingActivity extends BaseActivity {
    private RecyclerView rvRanking;
    private TextView tvBestScore;
    private static final String TAG = "RANKING_DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        String userId = AuthUtil.getCurrentUserId(this);
        int bestScore = AuthUtil.getCurrentBestScore(this);

        if (userId.isEmpty()) {
            Toast.makeText(this, "Error: user not logged in", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "No userId in SharedPreferences");
            return;
        }

        tvBestScore = findViewById(R.id.textView6);
        tvBestScore.setText("Your best score: " + bestScore);

        rvRanking = findViewById(R.id.rvRanking);
        rvRanking.setLayoutManager(new LinearLayoutManager(this));

        GameBuzzBlitzService api = RetrofitClient.getApiService();
        Log.d(TAG, "Calling getInfo with userId:: " + userId);
        api.getInfo(userId).enqueue(new Callback<InfoList>() {
            @Override
            public void onResponse(Call<InfoList> call, Response<InfoList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Info> rankingCompleto = response.body().getRanking();
                    int userPosition = response.body().getPosicionUsuario();


                    int nuevoBestScore = obtenerMejorPuntuacionUsuario(rankingCompleto, userId);
                    if (nuevoBestScore > bestScore) {
                        AuthUtil.setCurrentBestScore(RankingActivity.this, nuevoBestScore);
                        tvBestScore.setText("Your best score: " + nuevoBestScore);
                    }

                    Log.d(TAG, "Ranking received (size=" + rankingCompleto.size() + "):");
                    for (int i = 0; i < rankingCompleto.size(); i++) {
                        Info info = rankingCompleto.get(i);
                        Log.d(TAG, "Pos " + (i+1) + ": " + info.getUsuario() + " - Points: " + info.getMejorPuntuacion());
                    }
                    Log.d(TAG, "User position: " + userPosition);

                    List<Info> top5 = new ArrayList<>();
                    for (int i = 0; i < Math.min(5, rankingCompleto.size()); i++) {
                        top5.add(rankingCompleto.get(i));
                    }

                    if (userPosition > 5 && top5.size() == 5) {
                        Info userInfo = null;
                        for (Info info : rankingCompleto) {
                            if (info.getUsuario().equals(userId)) {
                                userInfo = info;
                                break;
                            }
                        }
                        if (userInfo != null) {
                            Log.d(TAG, "Replacing the last in top5 with the logged-in user: " + userInfo.getUsuario() + " - Puntos: " + userInfo.getMejorPuntuacion());
                            top5.set(4, userInfo);
                        } else {
                            Log.e(TAG, "Logged-in user not found in full ranking");
                        }
                    }

                    for (int i = 0; i < top5.size(); i++) {
                        Info info = top5.get(i);
                        Log.d(TAG, "Item " + i + ": " + info.getUsuario() + " - Points: " + info.getMejorPuntuacion());
                    }

                    MyRankingAdapter adapter = new MyRankingAdapter(top5, userId, userPosition);
                    rvRanking.setAdapter(adapter);
                } else {
                    Log.e(TAG, "Error fetching ranking data. Code: " + response.code());
                    Toast.makeText(RankingActivity.this, "Error obtaining data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InfoList> call, Throwable t) {
                Log.e(TAG, "Connexion error obtaining ranking", t);
                Toast.makeText(RankingActivity.this, "Conexion error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int obtenerMejorPuntuacionUsuario(List<Info> ranking, String userId) {
        for (Info info : ranking) {
            if (info.getUsuario().equals(userId)) {
                return info.getMejorPuntuacion();
            }
        }
        return AuthUtil.getCurrentBestScore(this);
    }
}