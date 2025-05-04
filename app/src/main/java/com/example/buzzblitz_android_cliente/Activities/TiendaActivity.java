package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buzzblitz_android_cliente.Adapters.MyShopAdapter;
import com.example.buzzblitz_android_cliente.Models.Objeto;
import com.example.buzzblitz_android_cliente.Models.ConsultaTienda;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.Services.BuzzBlitzService;
import com.example.buzzblitz_android_cliente.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaActivity extends BaseActivity {

    // Utilitza un RecyclerView per mostrar armes i skins que es descarreguen de l'API.
    // Es fan dues crides: una per armes i una altra per skins.
    // Quan arriben les dades, s'afegeixen a la llista i es notifica l'adaptador perquè es vegi tot.
    // Si l'API falla, es veurà als logs
    private RecyclerView rv;
    private MyShopAdapter adapter;
    private final List<Objeto> objetosTienda = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        // Configuro el RecyclerView
        rv = findViewById(R.id.rvObjetos);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyShopAdapter(objetosTienda);
        rv.setAdapter(adapter);

        // Carrego dades de la API
        cargarArmasYSkins();
    }

    private void cargarArmasYSkins() {
        BuzzBlitzService apiService = RetrofitClient.getApiService();

        // Crido a les armes
        apiService.getArmas().enqueue(new Callback<ConsultaTienda>() {
            @Override
            public void onResponse(Call<ConsultaTienda> call, Response<ConsultaTienda> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Objeto> armas = response.body().getConsulta();
                    objetosTienda.addAll(armas);
                    adapter.notifyDataSetChanged();
                    Log.d("API_TIENDA", "Armes loaded: " + armas.size());
                } else {
                    Log.e("API_ERROR", "Error weapons: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ConsultaTienda> call, Throwable t) {
                Log.e("API_FAILURE", "Error weapons: " + t.getMessage());
            }
        });

        // Crido a les skins
        apiService.getSkin().enqueue(new Callback<ConsultaTienda>() {
            @Override
            public void onResponse(Call<ConsultaTienda> call, Response<ConsultaTienda> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Objeto> skins = response.body().getConsulta();
                    objetosTienda.addAll(skins);
                    adapter.notifyDataSetChanged();
                    Log.d("API_TIENDA", "Skins loaded: " + skins.size());
                } else {
                    Log.e("API_ERROR", "Error skins: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ConsultaTienda> call, Throwable t) {
                Log.e("API_FAILURE", "Error skins: " + t.getMessage());
            }
        });
    }
}