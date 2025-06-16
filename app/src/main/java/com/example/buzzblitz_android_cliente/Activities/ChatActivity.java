package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.buzzblitz_android_cliente.Adapters.ChatAdapter;
import com.example.buzzblitz_android_cliente.Models.ChatIndividual;
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

public class ChatActivity extends BaseActivity {

    private RecyclerView rvChatMessages;
    private ChatAdapter adapter;
    private List<ChatIndividual> chatMessages = new ArrayList<>();
    private String currentUserName; // Cambiado de ID a nombre
    private String otherUserName;  // Cambiado de ID a nombre

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Obtener nombres en lugar de IDs
        currentUserName = getIntent().getStringExtra("CURRENT_USER_NAME");
        otherUserName = getIntent().getStringExtra("OTHER_USER_NAME");

        // Configurar RecyclerView
        rvChatMessages = findViewById(R.id.rvChatMessages);
        rvChatMessages.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(chatMessages, currentUserName);
        rvChatMessages.setAdapter(adapter);

        // Cargar mensajes
        loadMessages();

        // Configurar botón enviar
        EditText etMessageInput = findViewById(R.id.etMessageInput);
        LottieAnimationView btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(v -> {
            String message = etMessageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
                etMessageInput.setText("");
            }
        });
    }

    private void loadMessages() {
        GameBuzzBlitzService service = RetrofitClient.getApiService();
        // Usar nombres en la consulta
        service.getPrivateMessages(currentUserName, otherUserName).enqueue(new Callback<List<ChatIndividual>>() {
            @Override
            public void onResponse(Call<List<ChatIndividual>> call, Response<List<ChatIndividual>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chatMessages.clear();
                    chatMessages.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    rvChatMessages.scrollToPosition(chatMessages.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<List<ChatIndividual>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Error al cargar mensajes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage(String message) {
        ChatIndividual newMessage = new ChatIndividual();
        newMessage.setNameFrom(currentUserName); // Usar nombre
        newMessage.setNameTo(otherUserName);     // Usar nombre
        newMessage.setComentario(message);
        newMessage.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));

        GameBuzzBlitzService service = RetrofitClient.getApiService();
        service.sendPrivateMessage(newMessage).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    chatMessages.add(newMessage);
                    adapter.notifyItemInserted(chatMessages.size() - 1);
                    rvChatMessages.scrollToPosition(chatMessages.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Error al enviar mensaje", Toast.LENGTH_SHORT).show();
            }
        });
    }
}