package com.example.buzzblitz_android_cliente.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buzzblitz_android_cliente.Models.Badge;
import com.example.buzzblitz_android_cliente.R;
import java.util.List;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.ViewHolder> {
    private List<Badge> badges;

    public BadgeAdapter(List<Badge> badges) {
        this.badges = badges;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_badge, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Badge badge = badges.get(position);
        holder.tvName.setText(badge.getName());
        Glide.with(holder.itemView.getContext())
                .load(badge.getAvatar())
                .into(holder.imgBadge);
    }

    @Override
    public int getItemCount() {
        return badges != null ? badges.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBadge;
        TextView tvName;

        ViewHolder(View itemView) {
            super(itemView);
            imgBadge = itemView.findViewById(R.id.imgBadge);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}