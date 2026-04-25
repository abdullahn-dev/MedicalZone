package com.example.medicalzone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private List<Appointment> appointmentList;

    public AppointmentAdapter(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_item, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);
        holder.patientName.setText(appointment.getPatientName());
        holder.appointmentDate.setText("Date: " + appointment.getAppointmentDate());
        holder.appointmentTime.setText("Time: " + appointment.getAppointmentTime());
        holder.doctorName.setText("Doctor: " + appointment.getDoctorName());
        holder.reason.setText("Reason: " + appointment.getReason());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView patientName, appointmentDate, appointmentTime, doctorName, reason;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.patient_name);
            appointmentDate = itemView.findViewById(R.id.appointment_date);
            appointmentTime = itemView.findViewById(R.id.appointment_time);
            doctorName = itemView.findViewById(R.id.doctor_name);
            reason = itemView.findViewById(R.id.reason);
        }
    }
}
