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

public class AdminFragment extends Fragment {

    // Required empty public constructor
    public AdminFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        // Initialize the button
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnNavigateToAdminAuth = view.findViewById(R.id.admin_btn);

        // Set click listener to navigate to Admin Authentication Activity
        btnNavigateToAdminAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Admin Authentication Activity
                Intent intent = new Intent(getActivity(), LoginRegisterActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
