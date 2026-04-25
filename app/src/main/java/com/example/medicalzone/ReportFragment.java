package com.example.medicalzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ReportFragment extends Fragment {

    private Button viewReportsButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.report_fragment, container, false);

        // Initialize the button
        viewReportsButton = view.findViewById(R.id.viewReportsButton);

        // Set button click listener
        viewReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ReportActivity
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
