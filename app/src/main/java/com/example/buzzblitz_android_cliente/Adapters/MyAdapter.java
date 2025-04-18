package com.example.buzzblitz_android_cliente.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.buzzblitz_android_cliente.R;

import com.example.buzzblitz_android_cliente.Models.Objeto;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Objeto> objetos;

    public MyAdapter(List<Objeto> objetos) {
        this.objetos = objetos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // ¡Asegúrate de inflar activity_objeto.xml!
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_objeto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Objeto objeto = objetos.get(position);
        holder.tvNombre.setText(objeto.getNombre());
        holder.tvPrecio.setText("Precio: " + objeto.getPrecio());
        holder.tvTipo.setText("Tipo: " + objeto.getTipo());

        holder.imgObjeto.setImageResource(R.drawable.bee);
    }

    @Override
    public int getItemCount() {
        return objetos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgObjeto;
        TextView tvNombre, tvPrecio, tvTipo;

        public ViewHolder(View itemView) {
            super(itemView);
            imgObjeto = itemView.findViewById(R.id.imgObjeto);
            // ¡Los IDs deben coincidir con activity_objeto.xml!
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvTipo = itemView.findViewById(R.id.tvTipo);
        }
    }
}