package com.example.medicalzone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class AnswerQuestionsActivity extends AppCompatActivity {

    private EditText etAnswer;
    private Button btnSubmit;
    private TextView tvResponse;

    // Predefined symptoms and their corresponding answers and tips/advice
    private Map<String, String[]> symptomAnswers;
    // Predefined greetings
    private String[] greetings = {"hi", "hey", "hello", "salaam","sunao bawa jee"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_questions);

        // Initialize views
        etAnswer = findViewById(R.id.etAnswer);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvResponse = findViewById(R.id.tvResponse);

        // Initialize predefined symptom and answer mappings
        symptomAnswers = new HashMap<>();

        // Adding 50 symptoms with responses and tips
        symptomAnswers.put("fever", new String[]{
                "Fever is a common symptom of many infections and illnesses.",
                "Tip: Drink plenty of fluids and rest. If fever persists for more than 3 days, consult a doctor."
        });
        symptomAnswers.put("cough", new String[]{
                "A cough is often caused by colds or respiratory infections.",
                "Advice: Keep your throat moist by drinking warm liquids. If coughing persists for more than two weeks, see a doctor."
        });
        symptomAnswers.put("headache", new String[]{
                "Headaches can be due to stress, dehydration, or an illness.",
                "Tip: Drink water and relax. If the headache is severe or comes with nausea, consult a doctor."
        });
        symptomAnswers.put("fatigue", new String[]{
                "Fatigue can be caused by many factors, including illness and lack of rest.",
                "Advice: Ensure you're getting enough sleep and managing stress. If fatigue persists, see a healthcare provider."
        });
        symptomAnswers.put("sore throat", new String[]{
                "A sore throat is often associated with infections like the common cold.",
                "Tip: Gargle with warm salt water to soothe the throat. Stay hydrated and avoid smoking."
        });
        symptomAnswers.put("shortness of breath", new String[]{
                "Shortness of breath can result from conditions like asthma or a respiratory infection.",
                "Advice: If you're having difficulty breathing, try to stay calm. Seek immediate medical attention if it worsens."
        });
        symptomAnswers.put("nausea", new String[]{
                "Nausea is often linked to infections, food poisoning, or pregnancy.",
                "Tip: Drink clear fluids and eat small, frequent meals. If nausea persists, consider seeing a doctor."
        });
        symptomAnswers.put("diarrhea", new String[]{
                "Diarrhea can result from infections, food intolerances, or digestive issues.",
                "Tip: Stay hydrated by drinking water or electrolyte solutions. Avoid heavy or greasy foods until you recover."
        });
        symptomAnswers.put("stuffy nose", new String[]{
                "A stuffy nose is often caused by a cold or allergies.",
                "Advice: Use saline nasal spray to relieve congestion. Rest and avoid allergens if you have allergies."
        });
        symptomAnswers.put("chest pain", new String[]{
                "Chest pain may be caused by many conditions, including heart issues or lung conditions.",
                "Advice: If you experience severe chest pain or pressure, seek medical attention immediately as it could be a heart-related issue."
        });
        symptomAnswers.put("swelling", new String[]{
                "Swelling may indicate an allergic reaction or an underlying health condition.",
                "Tip: Elevate the affected area and apply cold compresses to reduce swelling. Consult a doctor for persistent or painful swelling."
        });
        symptomAnswers.put("rash", new String[]{
                "Rashes can be caused by infections, allergic reactions, or other skin conditions.",
                "Advice: Avoid scratching the rash and keep the area clean. Consult a healthcare provider if the rash doesn't go away."
        });
        symptomAnswers.put("itchy skin", new String[]{
                "Itchy skin may be caused by allergies, eczema, or insect bites.",
                "Tip: Moisturize the skin regularly and use anti-itch creams. If itching persists, seek medical advice."
        });
        symptomAnswers.put("dizziness", new String[]{
                "Dizziness may be caused by dehydration, low blood sugar, or an inner ear issue.",
                "Advice: Sit or lie down immediately if you feel dizzy. Drink water and eat something light. Seek medical advice if dizziness continues."
        });
        symptomAnswers.put("runny nose", new String[]{
                "A runny nose is commonly associated with colds or allergies.",
                "Tip: Use a saline nasal spray and rest. If symptoms persist for more than a week, see a doctor."
        });
        symptomAnswers.put("stomach ache", new String[]{
                "Stomach aches can be caused by indigestion, infections, or stress.",
                "Advice: Drink warm fluids, avoid spicy foods, and get plenty of rest. If pain worsens, seek medical attention."
        });
        symptomAnswers.put("cold", new String[]{
                "Cold symptoms are commonly caused by viruses like the common cold virus.",
                "Tip: Rest, hydrate, and eat nutritious food. Consult a doctor if symptoms persist beyond 10 days."
        });
        symptomAnswers.put("sweating", new String[]{
                "Excessive sweating can be caused by anxiety, fever, or certain medications.",
                "Advice: Try to stay cool and calm. If sweating is persistent or accompanied by other symptoms, see a doctor."
        });
        symptomAnswers.put("muscle pain", new String[]{
                "Muscle pain can be caused by exercise, strain, or viral infections.",
                "Tip: Rest and use a warm compress on the affected muscles. If pain persists, seek medical advice."
        });
        symptomAnswers.put("chills", new String[]{
                "Chills can be a sign of fever and may occur with infections.",
                "Advice: Keep yourself warm and rest. If chills persist, check for a fever and consult a doctor."
        });
        symptomAnswers.put("vomiting", new String[]{
                "Vomiting can be caused by infections, food poisoning, or stress.",
                "Tip: Rest and drink clear fluids to stay hydrated. If vomiting continues, consult a healthcare provider."
        });
        symptomAnswers.put("sore muscles", new String[]{
                "Sore muscles may be caused by physical exertion or tension.",
                "Advice: Stretch before and after exercises. Apply a warm compress for relief."
        });
        symptomAnswers.put("acne", new String[]{
                "Acne is often caused by hormonal changes or clogged pores.",
                "Tip: Use gentle skincare products, avoid touching your face, and consult a dermatologist if needed."
        });
        symptomAnswers.put("dry skin", new String[]{
                "Dry skin can be caused by environmental factors or dehydration.",
                "Advice: Use a hydrating moisturizer and drink plenty of water to keep your skin healthy."
        });
        symptomAnswers.put("hair loss", new String[]{
                "Hair loss can result from stress, hormonal imbalances, or nutritional deficiencies.",
                "Tip: Maintain a balanced diet, reduce stress, and consult a dermatologist if hair loss persists."
        });
        symptomAnswers.put("back pain", new String[]{
                "Back pain can be caused by poor posture, muscle strain, or injury.",
                "Advice: Practice good posture and engage in stretching exercises to relieve back pain."
        });
        symptomAnswers.put("stiff neck", new String[]{
                "A stiff neck is often caused by sleeping in an awkward position or muscle strain.",
                "Tip: Apply a warm compress and do neck stretches to alleviate stiffness."
        });
        symptomAnswers.put("earache", new String[]{
                "Earaches can be caused by infections or fluid buildup in the ear.",
                "Advice: Avoid inserting anything into the ear and consult a doctor if the pain persists."
        });
        symptomAnswers.put("blurry vision", new String[]{
                "Blurry vision can be caused by eye strain, dehydration, or an underlying health condition.",
                "Tip: Rest your eyes and blink frequently to reduce eye strain. Seek a professional eye check-up if needed."
        });
        symptomAnswers.put("high blood pressure", new String[]{
                "High blood pressure can increase the risk of heart disease or stroke.",
                "Tip: Exercise regularly, eat a balanced diet, and reduce sodium intake to manage blood pressure."
        });
        symptomAnswers.put("low blood pressure", new String[]{
                "Low blood pressure may cause dizziness, fainting, or weakness.",
                "Advice: Stay hydrated, avoid standing up quickly, and eat smaller meals more frequently."
        });
        symptomAnswers.put("high cholesterol", new String[]{
                "High cholesterol can increase the risk of heart disease.",
                "Tip: Eat a heart-healthy diet, exercise regularly, and avoid smoking to lower cholesterol levels."
        });
        symptomAnswers.put("constipation", new String[]{
                "Constipation can be caused by dehydration, lack of fiber, or stress.",
                "Tip: Increase your fiber intake, drink plenty of water, and stay active to prevent constipation."
        });
        symptomAnswers.put("irregular heartbeat", new String[]{
                "An irregular heartbeat can indicate heart-related conditions.",
                "Advice: Seek medical advice immediately if you experience chest pain or dizziness along with an irregular heartbeat."
        });
        symptomAnswers.put("bloody stool", new String[]{
                "Bloody stools can be caused by hemorrhoids, digestive issues, or more serious conditions.",
                "Advice: Avoid straining during bowel movements and see a doctor if the bleeding continues."
        });
        symptomAnswers.put("urinary incontinence", new String[]{
                "Urinary incontinence is the loss of bladder control.",
                "Tip: Practice pelvic floor exercises and consult a doctor for treatment options."
        });
        symptomAnswers.put("numbness", new String[]{
                "Numbness may occur due to poor circulation, nerve damage, or pressure on nerves.",
                "Advice: If numbness persists, seek medical attention as it may indicate a more serious issue."
        });
        symptomAnswers.put("dry mouth", new String[]{
                "Dry mouth can result from dehydration, medications, or certain medical conditions.",
                "Tip: Drink water frequently and use a mouth moisturizer if needed."
        });
        symptomAnswers.put("joint pain", new String[]{
                "Joint pain may be caused by arthritis, injury, or overuse.",
                "Advice: Rest, apply ice, and consult a healthcare provider if the pain persists."
        });
        symptomAnswers.put("cold hands", new String[]{
                "Cold hands may be due to poor circulation or cold temperatures.",
                "Tip: Keep your hands warm with gloves and consider improving circulation through exercise."
        });
        symptomAnswers.put("cold feet", new String[]{
                "Cold feet can be caused by poor circulation or cold weather.",
                "Advice: Keep your feet warm and active to improve circulation."
        });
        symptomAnswers.put("fatigue after eating", new String[]{
                "Fatigue after eating may be caused by a heavy meal or food sensitivity.",
                "Tip: Eat smaller, balanced meals and avoid overeating to prevent post-meal fatigue."
        });
        symptomAnswers.put("hot flashes", new String[]{
                "Hot flashes are often associated with menopause or hormonal changes.",
                "Advice: Stay cool, dress in layers, and talk to a healthcare provider if symptoms worsen."
        });
        symptomAnswers.put("dry eyes", new String[]{
                "Dry eyes can result from environmental factors, allergies, or prolonged screen time.",
                "Tip: Use artificial tears and take regular breaks from screens to reduce dryness."
        });

        // Set button click listener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = etAnswer.getText().toString().trim().toLowerCase();

                // Check for greetings first
                if (isGreeting(userAnswer)) {
                    tvResponse.setText("Hello! How can I assist you today?");
                }
                // Check for exit phrases like "no", "bye", "quit"
                else if (userAnswer.equals("no") || userAnswer.equals("bye") || userAnswer.equals("quit")) {
                    tvResponse.setText("Thank you for using our service. Goodbye!");
                } else {
                    String[] response = getResponse(userAnswer);

                    // Display the response
                    if (response != null) {
                        tvResponse.setText(response[0] + "\n\n" + "Tip: " + response[1]);
                    } else {
                        tvResponse.setText("Sorry, I couldn't understand your query. Please try again.");
                    }
                }

                // Clear the input field after displaying the response
                etAnswer.setText("");
            }
        });
    }

    // Method to match symptoms in the user's input with predefined symptoms
    private String[] getResponse(String answer) {
        // Loop through the predefined symptoms and their responses
        for (Map.Entry<String, String[]> entry : symptomAnswers.entrySet()) {
            // Check if the user's input contains the symptom keyword
            if (answer.contains(entry.getKey())) {
                return entry.getValue();  // Return the corresponding predefined answer and tip
            }
        }

        // If no match is found, return null
        return null;
    }

    // Method to check if the user's input is a greeting
    private boolean isGreeting(String input) {
        // Loop through the greeting words and check if any is in the user's input
        for (String greeting : greetings) {
            if (input.contains(greeting)) {
                return true;
            }
        }
        return false;
    }
}
