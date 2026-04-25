package com.example.medicalzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> implements Filterable {

    private List<Medicine> medicineList;
    private List<Medicine> medicineListFull; // Full list to store original items (for filtering)
    private Context context;

    public MedicineAdapter(List<Medicine> medicineList, Context context) {
        this.medicineList = medicineList;
        this.context = context;
        this.medicineListFull = new ArrayList<>(medicineList); // Make a copy of the list for filtering
    }

    @Override
    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicine, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicineViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);

        holder.medicineName.setText(medicine.getName());
        holder.medicinePrice.setText(medicine.getPrice());
        holder.medicineQuantity.setText(medicine.getQuantity());
        holder.medicineExpiryDate.setText(medicine.getExpiryDate());
        holder.medicineManufacturer.setText(medicine.getManufacturer());
        holder.medicineDescription.setText(medicine.getDescription());

        // Set the click listener to open the dialog
        holder.itemView.setOnClickListener(v -> {
            // Check if the context is an instance of BuyMedicinesActivity
            if (context instanceof BuyMedicinesActivity) {
                // Safe to cast context to BuyMedicinesActivity now
                ((BuyMedicinesActivity) context).showQuantityDialog(medicine);
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Medicine> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    // If the search query is empty, show the full list
                    filteredList.addAll(medicineListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    // Loop through the original list to filter by name or description
                    for (Medicine medicine : medicineListFull) {
                        if (medicine.getName().toLowerCase().contains(filterPattern) ||
                                medicine.getDescription().toLowerCase().contains(filterPattern)) {
                            filteredList.add(medicine);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null) {
                    medicineList.clear();
                    medicineList.addAll((List) results.values);
                    notifyDataSetChanged();
                }
            }
        };
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder {

        TextView medicineName, medicinePrice, medicineQuantity, medicineExpiryDate, medicineManufacturer, medicineDescription;

        public MedicineViewHolder(View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.medicine_name);
            medicinePrice = itemView.findViewById(R.id.medicine_price);
            medicineQuantity = itemView.findViewById(R.id.medicine_quantity);
            medicineExpiryDate = itemView.findViewById(R.id.medicine_expiry_date);
            medicineManufacturer = itemView.findViewById(R.id.medicine_manufacturer);
            medicineDescription = itemView.findViewById(R.id.medicine_description);
        }
    }
}
