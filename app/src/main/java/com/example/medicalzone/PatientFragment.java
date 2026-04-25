package com.example.medicalzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class PatientFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient, container, false);

        // Get a reference to the button
        Button btnNavigateAuth = view.findViewById(R.id.get_started_button);

        // Set a click listener for the button
        btnNavigateAuth.setOnClickListener(v -> {
            // Navigate to the Patient Authentication page
            Intent intent = new Intent(getActivity(), PatientAuthActivity.class);
            startActivity(intent);
        });

        return view;
    }
    }


