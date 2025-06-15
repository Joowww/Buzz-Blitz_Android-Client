package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Adapters.IssueAdapter;
import com.example.buzzblitz_android_cliente.Models.Issue;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssueListActivity extends BaseActivity {
    private static final String TAG = "IssueListActivity";
    private RecyclerView recyclerView;
    private IssueAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_list);
        recyclerView = findViewById(R.id.rvIssues);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IssueAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        loadIssues();
    }

    private void loadIssues() {
        GameBuzzBlitzService api = RetrofitClient.getApiService();
        Call<List<Issue>> call = api.getIssues();
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setIssues(response.body());
                } else {
                    String errorMsg = "Error: " + response.code() + " - " + response.message();
                    Toast.makeText(IssueListActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {
                Toast.makeText(IssueListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}