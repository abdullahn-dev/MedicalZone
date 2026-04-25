package com.example.medicalzone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.medicalzone.models.Doctor;

import java.util.ArrayList;
import java.util.List;

//public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
//
//    private List<Doctor> doctorList;
//    private List<Doctor> filteredList;
////    private final Context context;
//    private final DoctorClickListener clickListener;
//
//    public DoctorAdapter(List<Doctor> doctorList, DoctorClickListener clickListener) {
//        this.doctorList = doctorList;
//        this.filteredList = new ArrayList<>(doctorList);
////        this.context = context;
//        this.clickListener = clickListener;
//    }
//
//    @NonNull
//    @Override
//    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
//        return new DoctorViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
//        Doctor doctor = filteredList.get(position);
//        holder.tvDoctorName.setText(doctor.getName());
//        holder.tvSpecialization.setText(doctor.getSpecialization());
//        holder.tvExperience.setText(doctor.getExperience() + " years");
//
//        // Set profile picture
//        // Assuming Doctor model contains a profile picture resource ID
////        holder.ivProfile.setImageResource(doctor.getProfilePicture());
//
//        holder.btnBookAppointment.setOnClickListener(view -> clickListener.onDoctorClick(doctor));
//    }
//
//    @Override
//    public int getItemCount() {
//        return filteredList.size();
//    }
//
//    public void filter(String query) {
//        filteredList.clear();
//        if (query.isEmpty()) {
//            filteredList.addAll(doctorList);
//        } else {
//            for (Doctor doctor : doctorList) {
//                if (doctor.getName().toLowerCase().contains(query.toLowerCase()) ||
//                        doctor.getSpecialization().toLowerCase().contains(query.toLowerCase())) {
//                    filteredList.add(doctor);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
//
//    static class DoctorViewHolder extends RecyclerView.ViewHolder {
//
//        TextView tvDoctorName, tvSpecialization, tvExperience;
//        ImageView ivProfile;
//        Button btnBookAppointment;
//
//        public DoctorViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvDoctorName = itemView.findViewById(R.id.tvDoctorName);
//            tvSpecialization = itemView.findViewById(R.id.tvDoctorSpecialization);
//            tvExperience = itemView.findViewById(R.id.tvDoctorExperience);
////            ivProfile = itemView.findViewById(R.id.ivProfile);
////            btnBookAppointment = itemView.findViewById(R.id.btnBookAppointment);
//        }
//    }
//
//    public interface DoctorClickListener {
//        void onDoctorClick(Doctor doctor);
//    }
//}
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private List<Doctor> doctorList;

    public DoctorAdapter(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.doctorName.setText(doctor.getName());
        holder.doctorSpecialty.setText(doctor.getSpecialty());
       // holder.doctorExperience.setText("Experience: " + doctor.getExperience() + " years");
        //holder.doctorImage.setImageResource(doctor.getImageResId());
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView doctorName, doctorSpecialty, doctorExperience;
        ImageView doctorImage;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.doctor_name);
            doctorSpecialty = itemView.findViewById(R.id.doctor_specialty);
          //  doctorExperience = itemView.findViewById(R.id.doctor_experience);
            doctorImage = itemView.findViewById(R.id.doctor_image);
        }
    }
}
