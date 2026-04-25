package com.example.medicalzone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MedicineItemAdapter extends RecyclerView.Adapter<MedicineItemAdapter.MedicineViewHolder> {

    private List<Medicinee> medicineList; // Ensure the data model is consistent
    private Context context;

    public MedicineItemAdapter(List<Medicinee> medicineList, Context context) {
        this.medicineList = medicineList;
        this.context = context;
    }

    @Override
    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_medicine, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicineViewHolder holder, int position) {
        Medicinee medicine = medicineList.get(position);

        holder.medicineName.setText(medicine.getName());
        holder.medicinePrice.setText("Price: " + medicine.getPrice());
        holder.medicineQuantity.setText("Quantity: " + medicine.getQuantity());
        holder.medicineExpiryDate.setText("Expiry: " + medicine.getExpiryDate());
        holder.medicineManufacturer.setText("Manufacturer: " + medicine.getManufacturer());
        holder.medicineDescription.setText("Description: " + medicine.getDescription());

        // Handle update button click
        holder.updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateMedicineActivity.class);
            intent.putExtra("medicine_name", medicine.getName());
            intent.putExtra("medicine_price", medicine.getPrice());
            intent.putExtra("medicine_quantity", medicine.getQuantity());
            intent.putExtra("medicine_expiry", medicine.getExpiryDate());
            intent.putExtra("medicine_manufacturer", medicine.getManufacturer());
            intent.putExtra("medicine_description", medicine.getDescription());
            intent.putExtra("medicine_id", medicine.getDocumentId()); // Pass document ID
            context.startActivity(intent);
        });

        // Handle delete button click
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        holder.deleteButton.setOnClickListener(v -> {
            String documentId = medicine.getDocumentId(); // Ensure `getDocumentId()` method exists in `Medicine`

            // Delete the document from Firestore
            db.collection("medicines")
                    .document(documentId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(context, "Medicine deleted successfully", Toast.LENGTH_SHORT).show();
                        // Update RecyclerView by removing the item from the list
                        medicineList.remove(position);
                        notifyItemRemoved(position);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Failed to delete medicine: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    // ViewHolder class
    public class MedicineViewHolder extends RecyclerView.ViewHolder {
        TextView medicineName, medicinePrice, medicineQuantity, medicineExpiryDate, medicineManufacturer, medicineDescription;
        ImageButton updateButton, deleteButton;

        public MedicineViewHolder(View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.medicineName);
            medicinePrice = itemView.findViewById(R.id.medicinePrice);
            medicineQuantity = itemView.findViewById(R.id.medicineQuantity);
            medicineExpiryDate = itemView.findViewById(R.id.medicineExpiryDate);
            medicineManufacturer = itemView.findViewById(R.id.medicineManufacturer);
            medicineDescription = itemView.findViewById(R.id.medicineDescription);
            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
