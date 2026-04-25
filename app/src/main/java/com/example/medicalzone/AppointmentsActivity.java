package com.example.medicalzone;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppointmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppointmentAdapter adapter;
    private List<Appointment> appointmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointments_activity);

        recyclerView = findViewById(R.id.appointments_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appointmentList = new ArrayList<>();
        adapter = new AppointmentAdapter(appointmentList);
        recyclerView.setAdapter(adapter);

        fetchAppointmentsFromFirestore();
    }

    private void fetchAppointmentsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("appointments")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        appointmentList.clear();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String patientName = document.getString("patientName");

                            // Retrieve appointment date and time as long (timestamp)
                            Long appointmentDateMillis = document.getLong("appointmentDate");
                            Long appointmentTimeMillis = document.getLong("appointmentTime");

                            // Convert long (milliseconds) to Date
                            Date appointmentDate = appointmentDateMillis != null ? new Date(appointmentDateMillis) : new Date();
                            Date appointmentTime = appointmentTimeMillis != null ? new Date(appointmentTimeMillis) : new Date();

                            // Format the Date objects as Strings
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            String formattedDate = sdf.format(appointmentDate);
                            String formattedTime = sdf.format(appointmentTime);

                            String doctorName = document.getString("doctorName");
                            String reason = document.getString("reason");

                            // Create Appointment object with formatted date and time
                            Appointment appointment = new Appointment(patientName, formattedDate, formattedTime, doctorName, reason);
                            appointmentList.add(appointment);
                        }

                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(AppointmentsActivity.this, "No appointments found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AppointmentsActivity.this, "Error fetching appointments: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
