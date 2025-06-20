package com.example.buzzblitz_android_cliente.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Adapters.MyShopAdapter;
import com.example.buzzblitz_android_cliente.Models.AuthUtil;
import com.example.buzzblitz_android_cliente.Models.Usuario_objeto;
import com.example.buzzblitz_android_cliente.Models.DevolverCompra;
import com.example.buzzblitz_android_cliente.Models.Objeto;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;
import com.example.buzzblitz_android_cliente.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaActivity extends BaseActivity {
    private RecyclerView rv;
    private MyShopAdapter adapter;
    private final List<Objeto> objetosTienda = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    private final Set<String> purchasedItemsSync = new HashSet<>();
    private boolean armasLoaded = false;
    private boolean skinsLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        sharedPreferences = getSharedPreferences(AuthUtil.PREFS_NAME, MODE_PRIVATE);

        rv = findViewById(R.id.rvObjetos);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyShopAdapter(objetosTienda, this, sharedPreferences);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener((position, view) -> {
            Objeto objeto = objetosTienda.get(position);
            String usuarioId = AuthUtil.getCurrentUserId(this);

            Usuario_objeto usuarioobjeto = new Usuario_objeto();
            usuarioobjeto.setUsuario_id(usuarioId);
            usuarioobjeto.setObjeto(objeto.getNombre());

            GameBuzzBlitzService api = RetrofitClient.getApiService();
            Call<DevolverCompra> call = api.comprarObjeto(usuarioobjeto);
            call.enqueue(new Callback<DevolverCompra>() {
                @Override
                public void onResponse(Call<DevolverCompra> call, Response<DevolverCompra> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        int nuevosTarros = response.body().getTarrosMiel();

                        AuthUtil.setCurrentTarrosMiel(TiendaActivity.this, nuevosTarros);
                        adapter.actualizarTarrosMiel(nuevosTarros);

                        Set<String> purchasedItems = new HashSet<>(sharedPreferences.getStringSet("purchasedItems", new HashSet<>()));
                        purchasedItems.add(objeto.getId());
                        sharedPreferences.edit().putStringSet("purchasedItems", purchasedItems).apply();

                        adapter.notifyItemChanged(position);
                        Toast.makeText(TiendaActivity.this, "Honey jars left: " + nuevosTarros, Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMessage = "Error: ";
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(TiendaActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DevolverCompra> call, Throwable t) {
                    Toast.makeText(TiendaActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            });
        });

        sincronizarComprasUsuario();
        cargarArmasYSkins();
    }

    private void cargarArmasYSkins() {
        GameBuzzBlitzService apiService = RetrofitClient.getApiService();

        apiService.getArmas().enqueue(new Callback<List<Objeto>>() {
            @Override
            public void onResponse(Call<List<Objeto>> call, Response<List<Objeto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    objetosTienda.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Log.d("API_TIENDA", "Armas loaded: " + response.body().size());
                } else {
                    Log.e("API_ERROR", "Error weapons: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Objeto>> call, Throwable t) {
                Log.e("API_FAILURE", "Error weapons: " + t.getMessage());
            }
        });

        apiService.getSkins().enqueue(new Callback<List<Objeto>>() {
            @Override
            public void onResponse(Call<List<Objeto>> call, Response<List<Objeto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    objetosTienda.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Log.d("API_TIENDA", "Skins loaded: " + response.body().size());
                } else {
                    Log.e("API_ERROR", "Error skins: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Objeto>>call, Throwable t) {
                Log.e("API_FAILURE", "Error skins: " + t.getMessage());
            }
        });
    }

    private void sincronizarComprasUsuario() {
        String usuarioId = AuthUtil.getCurrentUserId(this);
        GameBuzzBlitzService api = RetrofitClient.getApiService();

        api.getArmasUsuario(usuarioId).enqueue(new Callback<List<Objeto>>() {
            @Override
            public void onResponse(Call<List<Objeto>> call, Response<List<Objeto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Objeto obj : response.body()) {
                        purchasedItemsSync.add(obj.getId());
                    }
                }
                armasLoaded = true;
                verificarYGuardarCompras();
            }

            @Override
            public void onFailure(Call<List<Objeto>> call, Throwable t) {
                Log.e("API_FAILURE", "Failed to sync purchased weapons: " + t.getMessage());
                armasLoaded = true;
                verificarYGuardarCompras();
            }
        });

        api.getSkinsUsuario(usuarioId).enqueue(new Callback<List<Objeto>>() {
            @Override
            public void onResponse(Call<List<Objeto>> call, Response<List<Objeto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Objeto obj : response.body()) {
                        purchasedItemsSync.add(obj.getId());
                    }
                }
                skinsLoaded = true;
                verificarYGuardarCompras();
            }

            @Override
            public void onFailure(Call<List<Objeto>> call, Throwable t) {
                Log.e("API_FAILURE", "Failed to sync purchased skins: " + t.getMessage());
                skinsLoaded = true;
                verificarYGuardarCompras();
            }
        });
    }

    private void verificarYGuardarCompras() {
        if (armasLoaded && skinsLoaded) {
            sharedPreferences.edit().putStringSet("purchasedItems", purchasedItemsSync).apply();
            adapter.notifyDataSetChanged();
        }
    }
}