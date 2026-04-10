package com.asif.farmeta;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BuyerPostAdapter extends RecyclerView.Adapter<BuyerPostAdapter.ViewHolder> {

    Context context;
    ArrayList<BuyerPostModel> list;

    public BuyerPostAdapter(Context context, ArrayList<BuyerPostModel> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, email, contact, location, crop, quantity, price;
        Button btnSee;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            contact = itemView.findViewById(R.id.contact);
            location = itemView.findViewById(R.id.location);
            crop = itemView.findViewById(R.id.crop);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            btnSee = itemView.findViewById(R.id.btnSee);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_buyer_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BuyerPostModel m = list.get(position);

        holder.name.setText("Name: " + m.getName());
        holder.email.setText("Email: " + m.getEmail());
        holder.contact.setText("Contact: " + m.getContact());
        holder.location.setText("Location: " + m.getLocation());
        holder.crop.setText("Crop: " + m.getCrop());
        holder.quantity.setText("Quantity: " + m.getQuantity());
        holder.price.setText("Price: " + m.getPrice());

        holder.btnSee.setOnClickListener(v -> showDialog(m));
    }

    private void showDialog(BuyerPostModel m) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_buyer, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button callBtn = view.findViewById(R.id.callBtn);
        Button locBtn = view.findViewById(R.id.locBtn);

        callBtn.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + m.getContact()));
            context.startActivity(intent);
        });

        locBtn.setOnClickListener(v -> {

            String uri = "https://www.google.com/maps/search/?api=1&query="
                    + Uri.encode(m.getLocation());

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}