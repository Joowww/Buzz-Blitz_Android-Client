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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssueListActivity extends AppCompatActivity {
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
        Call<ResponseBody> call = api.getIssues();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string();
                        JSONObject jsonObject = new JSONObject(jsonString);
                        JSONArray issuesArray = jsonObject.getJSONArray("entity");

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Issue>>(){}.getType();
                        List<Issue> issues = gson.fromJson(issuesArray.toString(), listType);

                        adapter.setIssues(issues);
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing issues: " + e.getMessage());
                        Toast.makeText(IssueListActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(IssueListActivity.this, "Failed to get issues", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(IssueListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
