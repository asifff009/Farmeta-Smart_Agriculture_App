package com.asif.farmeta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.*;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BuyerAdapter extends RecyclerView.Adapter<BuyerAdapter.ViewHolder> {

    Context context;
    ArrayList<BuyerModel> list;

    public BuyerAdapter(Context context, ArrayList<BuyerModel> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_buyer_gig, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        BuyerModel model = list.get(position);

        holder.name.setText(model.getName());
        holder.crop.setText("Crop: " + model.getCrop());
        holder.quantity.setText("Qty: " + model.getQuantity());
        holder.price.setText("Price: " + model.getPrice());
        holder.location.setText("Location: " + model.getLocation());

        holder.itemView.setOnClickListener(v -> {
            showDialog(model.getContact());
        });
    }

    private void showDialog(String phone){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Buyer Contact");
        builder.setMessage("Call Buyer?\n" + phone);

        builder.setPositiveButton("Call", (d,w)->{
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+phone));
            context.startActivity(intent);
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    public int getItemCount(){ return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, crop, quantity, price, location;

        public ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            crop = itemView.findViewById(R.id.crop);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            location = itemView.findViewById(R.id.location);
        }
    }
}