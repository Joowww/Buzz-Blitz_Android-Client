package com.example.buzzblitz_android_cliente.Adapters;

import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buzzblitz_android_cliente.Models.Video;
import com.example.buzzblitz_android_cliente.R;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private final List<Video> videos;

    public VideoAdapter(List<Video> videos) {
        this.videos = videos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.tvVideoUrl.setText(video.getVideo());

        holder.itemView.setOnClickListener(v -> {
            String url = video.getVideo();
            if (url != null && !url.trim().isEmpty()) {
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setPackage("com.google.android.youtube");
                try {
                    v.getContext().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    v.getContext().startActivity(webIntent);
                }
            } else {
                Toast.makeText(v.getContext(), "La URL of the video is empty.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvVideoUrl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVideoUrl = itemView.findViewById(R.id.tvVideoUrl);
        }
    }
}
