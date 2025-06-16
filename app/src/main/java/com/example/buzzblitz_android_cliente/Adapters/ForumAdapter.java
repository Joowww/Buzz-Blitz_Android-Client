package com.example.buzzblitz_android_cliente.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buzzblitz_android_cliente.Models.Forum;
import com.example.buzzblitz_android_cliente.R;
import java.util.List;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder> {
    private List<Forum> forumList;

    public ForumAdapter(List<Forum> forumList) {
        this.forumList = forumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forum_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Forum forum = forumList.get(position);
        holder.userName.setText(forum.getName());
        holder.message.setText(forum.getComentario());
        holder.timestamp.setText(forum.getDate());
    }

    @Override
    public int getItemCount() {
        return forumList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName, message, timestamp;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tvUserName);
            message = itemView.findViewById(R.id.tvMessage);
            timestamp = itemView.findViewById(R.id.tvDate);
            deleteButton = itemView.findViewById(R.id.btnDeleteForum);
        }
    }
}