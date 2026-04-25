package com.example.medicalzone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HealthTipAdapter extends RecyclerView.Adapter<HealthTipAdapter.HealthTipViewHolder> {

    private List<HealthArticle> healthArticles;
    private OnItemClickListener onItemClickListener;

    public HealthTipAdapter(List<HealthArticle> healthArticles) {
        this.healthArticles = healthArticles;
    }

    @NonNull
    @Override
    public HealthTipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_health_tip, parent, false);
        return new HealthTipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthTipViewHolder holder, int position) {
        HealthArticle healthArticle = healthArticles.get(position);
        holder.titleTextView.setText(healthArticle.getTitle());
        holder.descriptionTextView.setText(healthArticle.getDescription());
    }

    @Override
    public int getItemCount() {
        return healthArticles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class HealthTipViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView;

        public HealthTipViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewArticleTitle);
            descriptionTextView = itemView.findViewById(R.id.healthTipDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
