package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Adapters.FreqQuestAdapter;
import com.example.buzzblitz_android_cliente.Models.FreqQuest;
import com.example.buzzblitz_android_cliente.Models.ListFreqQuest;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;
import com.example.buzzblitz_android_cliente.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreqQuestActivity extends BaseActivity {

    private RecyclerView rvFaqs;

    private FreqQuestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listfreqquest);

        rvFaqs = findViewById(R.id.rvFreqQuest);

        adapter = new FreqQuestAdapter();
        rvFaqs.setLayoutManager(new LinearLayoutManager(this));
        rvFaqs.setAdapter(adapter);

        fetchFaqs();
    }

    private void fetchFaqs() {

        GameBuzzBlitzService api = RetrofitClient.getApiService();
        Call<ListFreqQuest> call = api.getFaqs();
        call.enqueue(new Callback<ListFreqQuest>() {
            @Override
            public void onResponse(Call<ListFreqQuest> call, Response<ListFreqQuest> response) {


                if (response.isSuccessful() && response.body() != null) {
                    List<FreqQuest> faqs = response.body().getFaqs();
                    adapter.setItems(faqs);
                } else {
                    Toast.makeText(FreqQuestActivity.this,
                            "Error loading FAQs (code " + response.code() + ")",
                            Toast.LENGTH_SHORT).show();
                    Log.e("FreqQuestActivity", "Code error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ListFreqQuest> call, Throwable t) {

                Toast.makeText(FreqQuestActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                Log.e("FreqQuestActivity", "onFailure", t);
            }
        });
    }
}
