package com.example.buzzblitz_android_cliente.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buzzblitz_android_cliente.Models.Objeto;
import com.example.buzzblitz_android_cliente.R;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private final List<Objeto> objetos;

    public MyAdapter(List<Objeto> objetos) {
        this.objetos = objetos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_objeto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Objeto objeto = objetos.get(position);
        Context context = holder.itemView.getContext();

        // Textos
        holder.tvNombre.setText(objeto.getNombre());
        holder.tvPrecio.setText("Precio: " + objeto.getPrecio());
        //holder.tvTipo.setText("Tipo: " + objeto.getTipo());
        holder.tvTipo.setText("Tipo: " + (objeto.getTipo() == 1 ? "Arma" : "Skin"));
        holder.tvDescripcion.setText("Descripción: " + objeto.getDescripcion());

        // Imagen desde recursos locales
        int resId = objeto.getImagenResId(context);
        holder.imgObjeto.setImageResource(resId != 0 ? resId : R.drawable.bee); // Fallback a "bee"
    }

    @Override
    public int getItemCount() {
        return objetos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgObjeto;
        TextView tvNombre, tvPrecio, tvTipo, tvDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgObjeto = itemView.findViewById(R.id.imgObjeto);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}