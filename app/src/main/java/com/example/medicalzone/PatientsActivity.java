package com.example.medicalzone;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import java.util.List;

public class PatientsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PatientAdapter adapter;
    List<Patient> patientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.patients_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize patient list
        patientList = new ArrayList<>();
        adapter = new PatientAdapter(patientList);
        recyclerView.setAdapter(adapter);

        // Fetch patient data from Firestore
        fetchPatients();
    }

    private void fetchPatients() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("patients").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot snapshot = task.getResult();
                        patientList.clear(); // Clear the existing list to avoid duplicates
                        for (DocumentSnapshot document : snapshot) {
                            Patient patient = document.toObject(Patient.class);
                            if (patient != null) {
                                patientList.add(patient);
                            }
                        }
                        adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
                    } else {
                        Toast.makeText(PatientsActivity.this, "Error fetching patients", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
