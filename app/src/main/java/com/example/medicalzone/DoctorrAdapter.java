package com.example.medicalzone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorrAdapter extends RecyclerView.Adapter<DoctorrAdapter.DoctorViewHolder> {

    private List<Doctorr> doctorList;
    private OnItemClickListener listener;

    public DoctorrAdapter(List<Doctorr> doctorList, OnItemClickListener listener) {
        this.doctorList = doctorList;
        this.listener = listener;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder holder, int position) {
        Doctorr doctor = doctorList.get(position);
        holder.nameTextView.setText(doctor.getName());
        holder.specialtyTextView.setText(doctor.getSpecialty());

        // Handle ImageButton click events
        holder.updateButton.setOnClickListener(v -> listener.onUpdateClick(doctor));
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(doctor));
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public interface OnItemClickListener {
        void onUpdateClick(Doctorr doctor);
        void onDeleteClick(Doctorr doctor);
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView specialtyTextView;
        public ImageButton updateButton;
        public ImageButton deleteButton;

        public DoctorViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.doctorName);
            specialtyTextView = itemView.findViewById(R.id.doctorSpecialty);
            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
