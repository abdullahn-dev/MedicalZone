package com.example.medicalzone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorWithImageAdapter extends RecyclerView.Adapter<DoctorWithImageAdapter.DoctorViewHolder> {

    private List<Doctor> doctorList;

    public DoctorWithImageAdapter(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_with_image, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.nameTextView.setText(doctor.getName());
        holder.specialtyTextView.setText(doctor.getSpecialty());
       // holder.imageView.setImageResource(doctor.getImageResId());
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, specialtyTextView;
        ImageView imageView;

        public DoctorViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.doctor_name);
            specialtyTextView = itemView.findViewById(R.id.doctor_specialty);
            imageView = itemView.findViewById(R.id.doctor_image);
        }
    }
}
