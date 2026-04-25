package com.example.medicalzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {

    private Spinner spinnerDisease;
    private RecyclerView recyclerViewTips;
    private TipsAdapter tipsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        // Initialize views
        spinnerDisease = findViewById(R.id.spinnerDisease);
        recyclerViewTips = findViewById(R.id.recyclerViewTips);

        // Set up RecyclerView
        recyclerViewTips.setLayoutManager(new LinearLayoutManager(this));
        tipsAdapter = new TipsAdapter(new ArrayList<>());
        recyclerViewTips.setAdapter(tipsAdapter);

        // Create an ArrayAdapter for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.diseases, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDisease.setAdapter(adapter);

        // Set the listener to handle the selected item
        spinnerDisease.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String disease = parentView.getItemAtPosition(position).toString();
                displayTips(disease);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case when no item is selected
            }
        });

        // Set up the FAB listener to navigate to a new activity for answering questions
        findViewById(R.id.fabSend).setOnClickListener(v -> {
            Intent intent = new Intent(ChatbotActivity.this, AnswerQuestionsActivity.class);
            startActivity(intent);
        });
    }

    private void displayTips(String disease) {
        List<String> tips = new ArrayList<>();

        // Use if-else statements to display corresponding tips for the disease
        if (disease.equals("Flu")) {
            tips.add("• Wash your hands regularly.");
            tips.add("• Avoid close contact with sick people.");
            tips.add("• Stay hydrated.");
            tips.add("• Rest.");
            tips.add("• Use a tissue when coughing.");
        } else if (disease.equals("Cough")) {
            tips.add("• Cover your mouth with a tissue or elbow when coughing.");
            tips.add("• Drink warm fluids.");
            tips.add("• Rest your voice.");
            tips.add("• Avoid smoking or irritants.");
            tips.add("• Use a humidifier to ease throat irritation.");
        } else if (disease.equals("Cold")) {
            tips.add("• Stay warm and rest.");
            tips.add("• Drink plenty of fluids.");
            tips.add("• Avoid cold weather.");
            tips.add("• Use a saline nasal spray.");
            tips.add("• Take vitamin C to boost immunity.");
        } else if (disease.equals("Headache")) {
            tips.add("• Stay in a quiet, dark room.");
            tips.add("• Avoid bright lights.");
            tips.add("• Drink water and stay hydrated.");
            tips.add("• Apply a cold compress to your forehead.");
            tips.add("• Take over-the-counter pain relievers.");
        } else if (disease.equals("Diarrhea")) {
            tips.add("• Stay hydrated.");
            tips.add("• Eat bland foods like bananas, rice, and toast.");
            tips.add("• Avoid dairy products.");
            tips.add("• Rest and avoid stress.");
            tips.add("• Wash hands thoroughly after using the bathroom.");
        } else if (disease.equals("Stomach Ache")) {
            tips.add("• Avoid heavy meals.");
            tips.add("• Drink ginger tea or peppermint tea.");
            tips.add("• Take antacids if needed.");
            tips.add("• Apply a heating pad to the stomach.");
            tips.add("• Avoid greasy or spicy foods.");
        } else if (disease.equals("Fever")) {
            tips.add("• Stay hydrated.");
            tips.add("• Rest and avoid physical exertion.");
            tips.add("• Take fever-reducing medications as prescribed.");
            tips.add("• Use a damp washcloth to cool your forehead.");
            tips.add("• Keep your room cool and well-ventilated.");
        } else if (disease.equals("Sore Throat")) {
            tips.add("• Gargle with warm salt water.");
            tips.add("• Drink warm teas or soups.");
            tips.add("• Rest your voice.");
            tips.add("• Avoid smoking or other irritants.");
            tips.add("• Use throat lozenges to soothe discomfort.");
        } else if (disease.equals("Allergy")) {
            tips.add("• Avoid allergens.");
            tips.add("• Take antihistamines as prescribed.");
            tips.add("• Keep windows closed during allergy season.");
            tips.add("• Use air purifiers to reduce allergens.");
            tips.add("• Wash your hands and face after being outside.");
        } else if (disease.equals("Fatigue")) {
            tips.add("• Get plenty of rest.");
            tips.add("• Eat healthy meals.");
            tips.add("• Avoid caffeine in the evening.");
            tips.add("• Engage in light physical activity.");
            tips.add("• Take short naps during the day.");
        } else if (disease.equals("Asthma")) {
            tips.add("• Avoid triggers such as smoke and dust.");
            tips.add("• Use an inhaler as prescribed.");
            tips.add("• Stay in a clean, dust-free environment.");
            tips.add("• Avoid extreme temperatures.");
            tips.add("• Get a flu shot to avoid respiratory infections.");
        } else if (disease.equals("Pneumonia")) {
            tips.add("• Get vaccinated against pneumonia.");
            tips.add("• Avoid smoking.");
            tips.add("• Rest and stay hydrated.");
            tips.add("• Use a humidifier to ease breathing.");
            tips.add("• Seek medical attention if symptoms worsen.");
        } else if (disease.equals("Migraine")) {
            tips.add("• Stay in a quiet, dark room.");
            tips.add("• Apply a cold compress to your forehead.");
            tips.add("• Avoid strong odors or bright lights.");
            tips.add("• Drink water to stay hydrated.");
            tips.add("• Take pain relievers as prescribed.");
        } else if (disease.equals("Chickenpox")) {
            tips.add("• Keep the skin cool and dry.");
            tips.add("• Avoid scratching the blisters.");
            tips.add("• Take antihistamines to reduce itching.");
            tips.add("• Keep nails short to avoid scratching.");
            tips.add("• Stay away from others until the blisters scab over.");
        } else if (disease.equals("Measles")) {
            tips.add("• Get vaccinated.");
            tips.add("• Stay away from others while contagious.");
            tips.add("• Keep your room ventilated.");
            tips.add("• Use a humidifier to help with breathing.");
            tips.add("• Stay hydrated and rest.");
        } else {
            tips.add("Please select a disease to get tips.");
        }

        // Update the RecyclerView with the selected disease's tips
        tipsAdapter.updateTips(tips);
    }
}
