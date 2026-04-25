package com.example.medicalzone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipsViewHolder> {

    private List<String> tips;

    public TipsAdapter(List<String> tips) {
        this.tips = tips;
    }

    @Override
    public TipsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new TipsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TipsViewHolder holder, int position) {
        holder.tipText.setText(tips.get(position));
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

    public void updateTips(List<String> newTips) {
        tips.clear();
        tips.addAll(newTips);
        notifyDataSetChanged();
    }

    public static class TipsViewHolder extends RecyclerView.ViewHolder {

        TextView tipText;

        public TipsViewHolder(View itemView) {
            super(itemView);
            tipText = itemView.findViewById(android.R.id.text1);
        }
    }
}
