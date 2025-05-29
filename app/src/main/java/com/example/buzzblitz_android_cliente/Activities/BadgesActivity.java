package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Adapters.BadgeAdapter;
import com.example.buzzblitz_android_cliente.Models.AuthUtil;
import com.example.buzzblitz_android_cliente.Models.Badge;
import com.example.buzzblitz_android_cliente.Models.BadgesResponse;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BadgesActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private BadgeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges);

        recyclerView = findViewById(R.id.rvBadges);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columnas

        String userId = AuthUtil.getCurrentUserId(this);
        fetchBadges(userId);
    }

    private void fetchBadges(String userId) {
        GameBuzzBlitzService service = RetrofitClient.getApiService();
        Call<BadgesResponse> call = service.getUserBadges(userId);

        call.enqueue(new Callback<BadgesResponse>() {
            @Override
            public void onResponse(Call<BadgesResponse> call, Response<BadgesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Badge> badges = response.body().getBadges();
                    adapter = new BadgeAdapter(badges);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<BadgesResponse> call, Throwable t) {
                Toast.makeText(BadgesActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}