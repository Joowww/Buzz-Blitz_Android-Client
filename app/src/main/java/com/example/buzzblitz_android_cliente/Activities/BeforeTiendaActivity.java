package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buzzblitz_android_cliente.R;

public class BeforeTiendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beforetienda); // Carga el layout activity_beforetienda.xml

        // Botón BACK -> MainActivity
        findViewById(R.id.btnBack).setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));

        // Botón COMPRAR -> TiendaPruebaActivity
        findViewById(R.id.btnComprarTienda).setOnClickListener(v ->
                startActivity(new Intent(this, TiendaActivity.class)));

        // Botón INTERCAMBIAR -> IntercambioActivity
        findViewById(R.id.btnIntercambiarTienda).setOnClickListener(v ->
                startActivity(new Intent(this, IntercambioActivity.class)));
    }
}