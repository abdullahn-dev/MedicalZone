package com.example.medicalzone;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PatienttAdapter extends RecyclerView.Adapter<PatienttAdapter.PatientViewHolder> {

    private Context context;
    private List<Patientt> patientList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onUpdateClick(Patientt patient);
        void onDeleteClick(Patientt patient);
    }

    public PatienttAdapter(Context context, List<Patientt> patientList, OnItemClickListener listener) {
        this.context = context;
        this.patientList = patientList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Patientt patient = patientList.get(position);

        if (patient != null) {
            holder.patientName.setText(patient.getName());
            holder.patientEmail.setText("Email: " + patient.getEmail());
            holder.patientIllness.setText("Illness: " + patient.getIllness());

            // Set up delete button action
            holder.deleteButton.setOnClickListener(v -> {
                Log.d("PatienttAdapter", "Delete button clicked for patient: " + patient.getName());
                listener.onDeleteClick(patient);
            });

            // Set up update button action
            holder.updateButton.setOnClickListener(v -> {
                Log.d("PatienttAdapter", "Update button clicked for patient: " + patient.getName());
                listener.onUpdateClick(patient);
            });
        }
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView patientName, patientEmail, patientIllness;
        ImageButton updateButton, deleteButton;

        public PatientViewHolder(View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.patientName);
            patientEmail = itemView.findViewById(R.id.patientEmail);
            patientIllness = itemView.findViewById(R.id.patientIllness);
            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
