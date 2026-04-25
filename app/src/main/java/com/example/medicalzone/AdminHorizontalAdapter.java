package com.example.medicalzone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminHorizontalAdapter extends RecyclerView.Adapter<AdminHorizontalAdapter.ViewHolder> {

    private List<CardItem> cardItems;
    private OnItemClickListener onItemClickListener;

    // Constructor
    public AdminHorizontalAdapter(List<CardItem> cardItems, OnItemClickListener listener) {
        this.cardItems = cardItems;
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the card_item_layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the card item for the current position
        CardItem cardItem = cardItems.get(position);

        // Set the image and title for the card
        holder.imageView.setImageResource(cardItem.getImageResource());
        holder.textView.setText(cardItem.getTitle());

        // Set the onClickListener for the card
        holder.cardView.setOnClickListener(v -> onItemClickListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    // ViewHolder class to hold the card's views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_icon);
            textView = itemView.findViewById(R.id.card_title);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
