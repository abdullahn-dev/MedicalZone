package com.example.medicalzone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorWithoutImageAdapter extends RecyclerView.Adapter<DoctorWithoutImageAdapter.DoctorViewHolder> {
    private List<Doctor> doctorList;

    // Constructor
    public DoctorWithoutImageAdapter(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_without_image, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.nameTextView.setText(doctor.getName());
        holder.specialtyTextView.setText(doctor.getSpecialty());
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, specialtyTextView;

        public DoctorViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.doctorName);
            specialtyTextView = itemView.findViewById(R.id.doctorSpecialty);
        }
    }
}
