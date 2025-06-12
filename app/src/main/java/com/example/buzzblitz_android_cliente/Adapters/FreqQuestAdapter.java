package com.example.buzzblitz_android_cliente.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Models.FreqQuest;
import com.example.buzzblitz_android_cliente.R;

import java.util.ArrayList;
import java.util.List;

public class FreqQuestAdapter extends RecyclerView.Adapter<FreqQuestAdapter.ViewHolder> {

    private final List<FreqQuest> items = new ArrayList<>();

    public void setItems(List<FreqQuest> faqs) {
        items.clear();
        items.addAll(faqs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FreqQuestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_frequest, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FreqQuestAdapter.ViewHolder holder, int position) {
        FreqQuest fq = items.get(position);
        holder.tvQuestion.setText(fq.getPregunta());
        holder.tvAnswer.setText(fq.getRespuesta());
        holder.tvDate.setText(fq.getDate());
        holder.tvSender.setText(fq.getSender());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvAnswer, tvDate, tvSender;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvAnswer   = itemView.findViewById(R.id.tvAnswer);
            tvDate     = itemView.findViewById(R.id.tvDate);
            tvSender   = itemView.findViewById(R.id.tvSender);
        }
    }
}
