package com.example.buzzblitz_android_cliente.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Models.Message;
import com.example.buzzblitz_android_cliente.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(chatView);
        return new MyViewHolder(chatView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);

        if (message.getSentBy().equals(Message.SENT_BY_ME)) {
            holder.leftChatView.setVisibility(View.GONE);
            holder.rightChatView.setVisibility(View.VISIBLE);
            holder.rightTextView.setText(message.getMessage());
        } else {
            holder.rightChatView.setVisibility(View.GONE);
            holder.leftChatView.setVisibility(View.VISIBLE);
            holder.leftTextView.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftChatView, rightChatView;
        TextView leftTextView, rightTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatView = itemView.findViewById(R.id.left_chat_view);
            rightChatView = itemView.findViewById(R.id.right_chat_view);
            leftTextView = itemView.findViewById(R.id.left_chat_text_view);
            rightTextView = itemView.findViewById(R.id.right_chat_text_view);
        }
    }
}




























//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.buzzblitz_android_cliente.Models.Mensaje;
//import com.example.buzzblitz_android_cliente.R;
//import java.util.List;
//
//public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
//    private List<Mensaje> mensajes;
//    private OnMessageActionListener listener;
//
//    public interface OnMessageActionListener {
//        void onEdit(int position, String mensaje);
//        void onDelete(int position);
//    }
//
//    public MessageAdapter(List<Mensaje> mensajes, OnMessageActionListener listener) {
//        this.mensajes = mensajes;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
//        return new MessageViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
//        Mensaje mensaje = mensajes.get(position);
//        holder.tvMessage.setText(mensaje.getAutor() + ": " + mensaje.getContenido());
//        holder.btnEdit.setOnClickListener(v -> listener.onEdit(position, mensaje.getContenido()));
//        holder.btnDelete.setOnClickListener(v -> listener.onDelete(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return mensajes.size();
//    }
//
//    public static class MessageViewHolder extends RecyclerView.ViewHolder {
//        TextView tvMessage;
//        ImageButton btnEdit, btnDelete;
//        public MessageViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvMessage = itemView.findViewById(R.id.tvMessage);
//            btnEdit = itemView.findViewById(R.id.btnEdit);
//            btnDelete = itemView.findViewById(R.id.btnDelete);
//        }
//    }
//}