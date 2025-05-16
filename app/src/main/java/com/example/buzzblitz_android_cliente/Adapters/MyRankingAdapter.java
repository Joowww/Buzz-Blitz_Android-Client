package com.example.buzzblitz_android_cliente.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buzzblitz_android_cliente.Models.Info;
import com.example.buzzblitz_android_cliente.Models.Objeto;
import com.example.buzzblitz_android_cliente.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyRankingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_TOP = 0;
    private static final int TYPE_USER = 1;

    private List<Info> fullRanking;
    private String currentUserId;
    private int userGlobalPosition = -1;

    public MyRankingAdapter(List<Info> fullRanking, String currentUserId) {
        this.fullRanking = fullRanking;
        this.currentUserId = currentUserId;

        // Buscar posición global del usuario
        for (int i = 0; i < fullRanking.size(); i++) {
            if (fullRanking.get(i).getUsuario().equals(currentUserId)) {
                userGlobalPosition = i;
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position < 5) ? TYPE_TOP : TYPE_USER;
    }

    @Override
    public int getItemCount() {
        return (userGlobalPosition >= 5 || userGlobalPosition == -1) ? 6 : 5;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOP) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ranking_top, parent, false);
            return new TopViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ranking_user, parent, false);
            return new UserViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder) {
            Info item = fullRanking.get(position);
            TopViewHolder topHolder = (TopViewHolder) holder;

            // Configurar colores
            int colorRes = R.color.white;
            if (position == 0) colorRes = R.color.gold;
            else if (position == 1) colorRes = R.color.silver;
            else if (position == 2) colorRes = R.color.bronze;
            topHolder.itemView.setBackgroundResource(colorRes);

            // Resaltar usuario
            if (item.getUsuario().equals(currentUserId)) {
                topHolder.tvNombreUsuario.setTypeface(null, Typeface.BOLD);
            }

            topHolder.tvPosicion.setText(String.valueOf(position + 1));
            topHolder.tvNombreUsuario.setText(item.getUsuario());
            topHolder.tvPuntuacion.setText(String.valueOf(item.getMejorPuntuacion()));

        } else if (holder instanceof UserViewHolder) {
            UserViewHolder userHolder = (UserViewHolder) holder;
            userHolder.tvPosicionUser.setText(String.valueOf(userGlobalPosition + 1));
            userHolder.tvNombreUsuarioUser.setText("Tú");
            userHolder.tvPuntuacionUser.setText(
                    String.valueOf(fullRanking.get(userGlobalPosition).getMejorPuntuacion())
            );
        }
    }

    // ViewHolders
    static class TopViewHolder extends RecyclerView.ViewHolder {
        TextView tvPosicion, tvNombreUsuario, tvPuntuacion;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPosicion = itemView.findViewById(R.id.tvPosicion);
            tvNombreUsuario = itemView.findViewById(R.id.tvNombreUsuario);
            tvPuntuacion = itemView.findViewById(R.id.tvPuntuacion);
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvPosicionUser, tvNombreUsuarioUser, tvPuntuacionUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPosicionUser = itemView.findViewById(R.id.tvPosicionUser);
            tvNombreUsuarioUser = itemView.findViewById(R.id.tvNombreUsuarioUser);
            tvPuntuacionUser = itemView.findViewById(R.id.tvPuntuacionUser);
        }
    }
}