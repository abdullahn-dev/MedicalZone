package com.example.medicalzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItemList;

    public CartAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.nameTextView.setText(cartItem.getName());
        holder.priceTextView.setText(String.format("$%.2f", cartItem.getPrice()));
        holder.quantityTextView.setText("Quantity: " + cartItem.getQuantity());
        holder.descriptionTextView.setText(cartItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView, priceTextView, quantityTextView, descriptionTextView;

        public CartViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textName);
            priceTextView = itemView.findViewById(R.id.textPrice);
            quantityTextView = itemView.findViewById(R.id.textQuantity);
            descriptionTextView = itemView.findViewById(R.id.textDescription);
        }
    }
}
