package com.asif.farmeta;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BuyerAdapter extends RecyclerView.Adapter<BuyerAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<BuyerModel> list;

    public BuyerAdapter(Context context, ArrayList<BuyerModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_buyer_gig, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        BuyerModel model = list.get(position);

        holder.name.setText(model.getName());
        holder.crop.setText("Crop: " + model.getCrop());
        holder.quantity.setText("Qty: " + model.getQuantity());
        holder.price.setText("Price: " + model.getPrice());
        holder.location.setText("Location: " + model.getLocation());

        // Button click for dialog
        holder.btnSelect.setOnClickListener(v -> {
            // Inflate custom dialog view
            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_buyer_action, null);

            // Build dialog with custom background
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setView(dialogView)
                    .create();

            // Optional: set transparent background to see rounded corners
            if(dialog.getWindow() != null)
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);

            Button callBtn = dialogView.findViewById(R.id.btnCallDialog);
            Button locationBtn = dialogView.findViewById(R.id.btnLocationDialog);

            // Call buyer
            callBtn.setOnClickListener(vv -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + model.getContact()));
                    context.startActivity(intent);
                } catch(Exception e){
                    Toast.makeText(context, "Cannot make call", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                dialog.dismiss();
            });

            // Show location
            locationBtn.setOnClickListener(vv -> {
                try {
                    String geoUri = "geo:0,0?q=" + Uri.encode(model.getLocation());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                    if(intent.resolveActivity(context.getPackageManager()) != null){
                        context.startActivity(intent);
                    } else {
                        // fallback to browser maps
                        String mapsUrl = "https://www.google.com/maps/search/?api=1&query=" + Uri.encode(model.getLocation());
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl));
                        context.startActivity(browserIntent);
                    }
                } catch(Exception e){
                    Toast.makeText(context, "Cannot open location", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                dialog.dismiss();
            });

            dialog.show();
        });
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, crop, quantity, price, location;
        Button btnSelect;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            crop = itemView.findViewById(R.id.crop);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            location = itemView.findViewById(R.id.location);
            btnSelect = itemView.findViewById(R.id.btnSelect);
        }
    }
}