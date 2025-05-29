package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buzzblitz_android_cliente.Models.Video;
import com.example.buzzblitz_android_cliente.Models.VideoListDTO;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;
import com.example.buzzblitz_android_cliente.Adapters.VideoAdapter;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideosActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private final List<Video> videoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        recyclerView = findViewById(R.id.rvVideos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(videoList);
        recyclerView.setAdapter(adapter);

        loadVideos();
    }

    private void loadVideos() {
        GameBuzzBlitzService api = RetrofitClient.getApiService();
        Call<VideoListDTO> call = api.getVideos();

        call.enqueue(new Callback<VideoListDTO>() {
            @Override
            public void onResponse(Call<VideoListDTO> call, Response<VideoListDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    videoList.addAll(response.body().getVideos());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(VideosActivity.this, "Error loading videos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VideoListDTO> call, Throwable t) {
                Log.e("VIDEOS_ERROR", "API call failed: " + t.getMessage());
                Toast.makeText(VideosActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
