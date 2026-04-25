package com.example.medicalzone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DoctorFragment extends Fragment {

    public DoctorFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor, container, false);

        // Find the button in the layout
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnDoctorAuth = view.findViewById(R.id.navi_1);

        // Set a click listener to navigate to DoctorAuthActivity
        btnDoctorAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start DoctorAuthActivity
                Intent intent = new Intent(getActivity(), DoctorAuthActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
