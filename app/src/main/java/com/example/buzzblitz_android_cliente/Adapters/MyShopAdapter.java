package com.example.buzzblitz_android_cliente.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.buzzblitz_android_cliente.Models.Objeto;
import com.example.buzzblitz_android_cliente.R;

import java.util.List;

public class MyShopAdapter extends RecyclerView.Adapter<MyShopAdapter.ViewHolder> {

    private final List<Objeto> objetos;
    private static final String TAG = "ShopAdapter"; // Etiqueta para logs

    public MyShopAdapter(List<Objeto> objetos) {
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

        // Configurar textos
        holder.tvNombre.setText(objeto.getNombre());
        holder.tvPrecio.setText("Precio: " + objeto.getPrecio());
        holder.tvTipo.setText("Tipo: " + (objeto.getTipo() == 1 ? "Arma" : "Skin"));
        holder.tvDescripcion.setText("Descripción: " + objeto.getDescripcion());

        // Logs de depuración
        Log.d(TAG, "Posición: " + position);
        Log.d(TAG, "Nombre objeto: " + objeto.getNombre());
        Log.d(TAG, "Nombre imagen: " + objeto.getImagen());

        // Construir URL de la imagen
        String imageUrl = "http://10.0.2.2:8080/dsaApp/public/img" + objeto.getImagen();
        Log.d(TAG, "URL completa: " + imageUrl);

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.bee)
                .error(R.drawable.bee) // Imagen de error
                .into(holder.imgObjeto);
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