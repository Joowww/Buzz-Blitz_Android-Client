package com.example.buzzblitz_android_cliente.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Models.Issue;
import com.example.buzzblitz_android_cliente.R;

import java.util.List;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.ViewHolder> {
    private List<Issue> issues;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvDate, tvSender, tvMessage;
        public ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvDate = v.findViewById(R.id.tvDate);
            tvSender = v.findViewById(R.id.tvSender);
            tvMessage = v.findViewById(R.id.tvMessage);
        }
    }

    public IssueAdapter(List<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_issue, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Issue issue = issues.get(position);
        holder.tvTitle.setText(issue.getTitle());
        holder.tvDate.setText(issue.getDate());
        holder.tvSender.setText(issue.getSender());
        holder.tvMessage.setText(issue.getMessage());
    }

    @Override
    public int getItemCount() {
        return issues.size();
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
        notifyDataSetChanged();
    }
}
