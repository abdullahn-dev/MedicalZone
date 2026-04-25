package com.example.medicalzone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private List<Patient> patientList;

    public PatientAdapter(List<Patient> patientList) {
        this.patientList = patientList;
    }

    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PatientViewHolder holder, int position) {
        Patient patient = patientList.get(position);
        holder.nameTextView.setText(patient.getName());
        holder.ageTextView.setText(String.valueOf(patient.getAge()));
        holder.illnessTextView.setText(patient.getIllness());
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, ageTextView, illnessTextView;

        public PatientViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.patient_name);
            ageTextView = itemView.findViewById(R.id.patient_age);
            illnessTextView = itemView.findViewById(R.id.patient_illness);
        }
    }
}
