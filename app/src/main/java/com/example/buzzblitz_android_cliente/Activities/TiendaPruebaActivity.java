package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buzzblitz_android_cliente.Adapters.MyAdapter;
import com.example.buzzblitz_android_cliente.Models.Objeto;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.Services.BuzzBlitzService;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaPruebaActivity extends AppCompatActivity {
    private RecyclerView rv;
    private MyAdapter adapter;
    private final List<Objeto> objetosTienda = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        // Configurar RecyclerView
        rv = findViewById(R.id.rvObjetos);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(objetosTienda);
        rv.setAdapter(adapter);

        // Cargar datos de la API
        cargarArmasYSkins();
    }

    private void cargarArmasYSkins() {
        BuzzBlitzService apiService = RetrofitClient.getApiService();

        // Llamada para armas
        apiService.getArmas().enqueue(new Callback<List<Objeto>>() {
            @Override
            public void onResponse(Call<List<Objeto>> call, Response<List<Objeto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    objetosTienda.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Log.d("API_TIENDA", "Armas cargadas: " + response.body().size());
                } else {
                    Log.e("API_ERROR", "Error armas: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Objeto>> call, Throwable t) {
                Log.e("API_FAILURE", "Error armas: " + t.getMessage());
            }
        });

        // Llamada para skins
        apiService.getSkin().enqueue(new Callback<List<Objeto>>() {
            @Override
            public void onResponse(Call<List<Objeto>> call, Response<List<Objeto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    objetosTienda.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Log.d("API_TIENDA", "Skins cargadas: " + response.body().size());
                } else {
                    Log.e("API_ERROR", "Error skins: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Objeto>> call, Throwable t) {
                Log.e("API_FAILURE", "Error skins: " + t.getMessage());
            }
        });
    }
}