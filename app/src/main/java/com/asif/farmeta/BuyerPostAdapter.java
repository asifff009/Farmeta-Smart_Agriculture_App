package com.asif.farmeta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BuyerPostAdapter extends RecyclerView.Adapter<BuyerPostAdapter.ViewHolder> {

    Context context;
    ArrayList<BuyerPostModel> list;

    public BuyerPostAdapter(Context context, ArrayList<BuyerPostModel> list){
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, contact, location, crops;
        Button btnSee;

        public ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.name);
            contact = itemView.findViewById(R.id.contact);
            location = itemView.findViewById(R.id.location);
            crops = itemView.findViewById(R.id.crops);
            btnSee = itemView.findViewById(R.id.btnSee);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_buyer_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){

        BuyerPostModel model = list.get(position);

        holder.name.setText(model.name);
        holder.contact.setText(model.contact);
        holder.location.setText(model.location);

        StringBuilder sb = new StringBuilder();

        for(BuyerPostModel.CropItem item : model.items){
            sb.append("Crop: ")
                    .append(item.crop)
                    .append(" | Qty: ")
                    .append(item.quantity)
                    .append(" | Price: ")
                    .append(item.price)
                    .append("\n");
        }

        holder.crops.setText(sb.toString());

        // ⭐ SEE BUTTON CLICK
        holder.btnSee.setOnClickListener(v -> showDialog(model));
    }

    private void showDialog(BuyerPostModel model){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_buyer_action, null);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button callBtn = view.findViewById(R.id.callBtn);
        Button locBtn = view.findViewById(R.id.locBtn);

        // 📞 CALL
        callBtn.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + model.contact));
            context.startActivity(intent);
        });

        // 📍 LOCATION
        locBtn.setOnClickListener(v -> {

            String uri = "https://www.google.com/maps/search/?api=1&query="
                    + Uri.encode(model.location);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount(){
        return list.size();
    }
}