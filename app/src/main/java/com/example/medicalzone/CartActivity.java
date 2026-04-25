package com.example.medicalzone;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerCart;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;
    private Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerCart = findViewById(R.id.recyclerCart);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Initialize RecyclerView
        recyclerCart.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the cart list
        cartItemList = new ArrayList<>();

        // Initialize the adapter with an empty list for now
        cartAdapter = new CartAdapter(this, cartItemList);

        recyclerCart.setAdapter(cartAdapter);

        // Fetch the cart data from Firestore
        fetchCartData();

        // Set up the checkout button
        btnCheckout.setOnClickListener(v -> showCheckoutDialog());
    }

    // Fetch cart data from Firestore
    private void fetchCartData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("userCart")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(CartActivity.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                    } else {
                        cartItemList.clear();
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String name = documentSnapshot.getString("name");
                            double price = documentSnapshot.getDouble("price");
                            String description = documentSnapshot.getString("description");
                            long quantity = documentSnapshot.getLong("quantity");

                            CartItem cartItem = new CartItem(name, price, description, quantity);
                            cartItemList.add(cartItem);
                        }

                        cartAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(CartActivity.this, "Error fetching cart data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("CartActivity", "Error fetching cart data", e);
                });
    }

    // Show a confirmation dialog for checkout
    private void showCheckoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Checkout")
                .setMessage("Are you sure you want to checkout? This will clear your cart.")
                .setPositiveButton("Yes", (dialog, which) -> checkoutCart())
                .setNegativeButton("No", null)
                .show();
    }

    private void checkoutCart() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("userCart")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    double totalBill = 0.0;

                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        double price = documentSnapshot.getDouble("price");
                        long quantity = documentSnapshot.getLong("quantity");
                        totalBill += (price * quantity);

                        // Delete each item
                        db.collection("userCart").document(documentSnapshot.getId()).delete();
                    }

                    // Calculate profit and store all details
                    double profit = totalBill * 0.20;
                    double actualSales = totalBill - profit;

                    SalesSummary salesSummary = new SalesSummary(totalBill, profit, actualSales);

                    // Save the sales data to Firestore
                    db.collection("sales")
                            .document("summary")
                            .set(salesSummary)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(CartActivity.this, "Checkout successful!", Toast.LENGTH_SHORT).show();
                                cartItemList.clear();
                                cartAdapter.notifyDataSetChanged();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(CartActivity.this, "Error updating sales: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("CartActivity", "Error updating sales", e);
                            });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(CartActivity.this, "Error clearing cart: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("CartActivity", "Error clearing cart", e);
                });
    }

}
