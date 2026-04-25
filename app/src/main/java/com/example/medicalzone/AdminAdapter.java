package com.example.medicalzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    private ArrayList<String> dataList;
    private String type; // "patient" or "doctor"
    private AdminDBHelper dbHelper;
    private Context context;

    public AdminAdapter(ArrayList<String> dataList, String type, AdminDBHelper dbHelper, Context context) {
        this.dataList = dataList;
        this.type = type;
        this.dbHelper = dbHelper;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String record = dataList.get(position);
        holder.textView.setText(record);

        // Delete Button
        holder.deleteButton.setOnClickListener(v -> {
//            dbHelper.deleteRecord(type, record);
            dataList.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, type + " deleted", Toast.LENGTH_SHORT).show();
        });

        // Edit Button (Placeholder)
        holder.editButton.setOnClickListener(v -> {
            Toast.makeText(context, "Edit functionality pending", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton deleteButton, editButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.itemText);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }


}
