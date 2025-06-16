package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Adapters.ForumAdapter;
import com.example.buzzblitz_android_cliente.Models.Forum;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForumActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ForumAdapter adapter;
    private List<Forum> forumMessages = new ArrayList<>();
    private EditText textAEnviar;
    private String currentUserName; // Cambiado de ID a nombre

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        // Obtener el nombre del usuario en lugar del ID
        currentUserName = getIntent().getStringExtra("USER_NAME");
        recyclerView = findViewById(R.id.recyclerView);
        textAEnviar = findViewById(R.id.textAEnviar);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ForumAdapter(forumMessages);
        recyclerView.setAdapter(adapter);

        // Cargar mensajes
        loadForumMessages();

        // Botón enviar
        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(v -> postMessage());
    }

    private void loadForumMessages() {
        GameBuzzBlitzService service = RetrofitClient.getApiService();
        service.getForumMessages().enqueue(new Callback<List<Forum>>() {
            @Override
            public void onResponse(Call<List<Forum>> call, Response<List<Forum>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    forumMessages.clear();
                    forumMessages.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(forumMessages.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<List<Forum>> call, Throwable t) {
                Toast.makeText(ForumActivity.this, "Error al cargar mensajes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postMessage() {
        String message = textAEnviar.getText().toString().trim();
        if (message.isEmpty()) return;

        Forum newMessage = new Forum();
        newMessage.setName(currentUserName); // Usar nombre en lugar de ID
        newMessage.setComentario(message);
        newMessage.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));

        GameBuzzBlitzService service = RetrofitClient.getApiService();
        service.postForumMessage(newMessage).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    textAEnviar.setText("");
                    loadForumMessages();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ForumActivity.this, "Error al enviar mensaje", Toast.LENGTH_SHORT).show();
            }
        });
    }
}