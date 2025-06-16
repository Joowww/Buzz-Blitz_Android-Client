package com.example.buzzblitz_android_cliente.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buzzblitz_android_cliente.Models.ChatIndividual;
import com.example.buzzblitz_android_cliente.R;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MSG_TYPE_SENT = 1;
    private static final int MSG_TYPE_RECEIVED = 2;

    private List<ChatIndividual> chatMessages;
    private String currentUserId;

    public ChatAdapter(List<ChatIndividual> chatMessages, String currentUserId) {
        this.chatMessages = chatMessages;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_SENT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatIndividual message = chatMessages.get(position);

        if (holder.getItemViewType() == MSG_TYPE_SENT) {
            ((SentMessageHolder) holder).bind(message);
        } else {
            ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chatMessages.get(position).getNameFrom().equals(currentUserId) ?
                MSG_TYPE_SENT : MSG_TYPE_RECEIVED;
    }

    static class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        ImageButton deleteButton;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.tvMessage);
            timeText = itemView.findViewById(R.id.tvTime);
            deleteButton = itemView.findViewById(R.id.btnDelete);
        }

        void bind(ChatIndividual message) {
            messageText.setText(message.getComentario());
            timeText.setText(message.getDate());
        }
    }

    static class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, senderText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.tvMessage);
            timeText = itemView.findViewById(R.id.tvTime);
            senderText = itemView.findViewById(R.id.tvSender);
        }

        void bind(ChatIndividual message) {
            messageText.setText(message.getComentario());
            timeText.setText(message.getDate());
            senderText.setText(message.getNameFrom());
        }
    }
}