package com.example.medicalzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LabTestAdapter extends RecyclerView.Adapter<LabTestAdapter.LabTestViewHolder> {

    private Context context;
    private List<LabTest> labTests;
    private OnLabTestClickListener onLabTestClickListener;

    public LabTestAdapter(Context context, List<LabTest> labTests, OnLabTestClickListener onLabTestClickListener) {
        this.context = context;
        this.labTests = labTests;
        this.onLabTestClickListener = onLabTestClickListener;
    }

    @Override
    public LabTestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lab_test, parent, false);
        return new LabTestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LabTestViewHolder holder, int position) {
        LabTest labTest = labTests.get(position);
        holder.name.setText(labTest.getName());
        holder.price.setText("$" + labTest.getPrice());
        holder.description.setText(labTest.getDescription());  // Bind the description

        holder.itemView.setOnClickListener(v -> onLabTestClickListener.onLabTestClick(labTest));
    }

    @Override
    public int getItemCount() {
        return labTests.size();
    }

    public static class LabTestViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, description;

        public LabTestViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.labTestName);
            price = itemView.findViewById(R.id.labTestPrice);
            description = itemView.findViewById(R.id.labTestDescription);  // Initialize description TextView
        }
    }

    public interface OnLabTestClickListener {
        void onLabTestClick(LabTest labTest);
    }
}
