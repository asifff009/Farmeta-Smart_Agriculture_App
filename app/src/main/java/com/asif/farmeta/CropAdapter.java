package com.asif.farmeta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.ViewHolder> {

    Context context;
    ArrayList<CropModel> list;

    public CropAdapter(Context context, ArrayList<CropModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_crop,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        CropModel model = list.get(position);
        holder.name.setText(model.getCropName());
        holder.seller.setText(model.getFullName());
        holder.details.setText(
                "Qty: " + model.getQuantity() + " | Price: " + model.getPrice() +
                        "\nContact: " + model.getContact() +
                        "\nAddress: " + model.getAddress()
        );
        holder.type.setText(model.getType()); // type fix

        Glide.with(context)
                .load("http://192.168.1.102/farmeta_api/uploads/" + model.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.image);
    }

    @Override
    public int getItemCount(){ return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, seller, details, type;
        ImageView image;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            seller = itemView.findViewById(R.id.seller);
            details = itemView.findViewById(R.id.details);
            type = itemView.findViewById(R.id.type);
            image = itemView.findViewById(R.id.image);
        }
    }
}