package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Adapters.UserAdapter;
import com.example.buzzblitz_android_cliente.Models.AuthUtil;
import com.example.buzzblitz_android_cliente.Models.Usuario;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends BaseActivity {

    private RecyclerView rvUsers;
    private UserAdapter adapter;
    private List<Usuario> users = new ArrayList<>();
    private String currentUserName; // Cambiado de ID a nombre

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        // Obtener el NOMBRE del usuario actual desde AuthUtil
        currentUserName = AuthUtil.getCurrentUserName(this);

        // Configurar RecyclerView
        rvUsers = findViewById(R.id.rvAllUsers);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(users, user -> startChat(user));
        rvUsers.setAdapter(adapter);

        // Cargar usuarios
        loadUsers();
    }

    private void loadUsers() {
        GameBuzzBlitzService service = RetrofitClient.getApiService();
        service.getAllUsers().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    users.clear();
                    // Filtrar usuario actual usando el ID (no el nombre)
                    String currentUserId = AuthUtil.getCurrentUserId(UserListActivity.this);
                    for (Usuario user : response.body()) {
                        if (!user.getId().equals(currentUserId)) {
                            users.add(user);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(UserListActivity.this, "Error al cargar usuarios", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startChat(Usuario user) {
        Intent intent = new Intent(this, ChatActivity.class);
        // Pasar NOMBRES en lugar de IDs
        intent.putExtra("CURRENT_USER_NAME", currentUserName);
        intent.putExtra("OTHER_USER_NAME", user.getName());
        startActivity(intent);
    }
}