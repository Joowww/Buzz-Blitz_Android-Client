package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Models.Objeto;
import com.example.buzzblitz_android_cliente.Adapters.MyAdapter;
import com.example.buzzblitz_android_cliente.R;

import java.util.ArrayList;
import java.util.List;

public class TiendaPruebaActivity extends AppCompatActivity {

    private RecyclerView rv;
    private MyAdapter adapter;
    private List<Objeto> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        // 1) Referencia al RecyclerView
        rv = findViewById(R.id.rvObjetos);

        // 2) LayoutManager lineal (vertical)
        rv.setLayoutManager(new LinearLayoutManager(this));

        // 3) Crear datos de ejemplo
        datos = new ArrayList<>();
        datos.add(new Objeto("1", "Pistola", 100, 0, R.drawable.bee));
        datos.add(new Objeto("2", "Skin Azul", 50, 1, R.drawable.bee));
        // … añade más Objetos según necesites

        // 4) Crear adapter con lista
        adapter = new MyAdapter(datos);

        // 5) Asignarlo al RecyclerView
        rv.setAdapter(adapter);
        Log.d("TiendaPrueba", "Adapter asignado, tamaño lista = " + datos.size());

    }
}