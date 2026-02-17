package com.asif.farmeta;

import android.content.Context;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.ViewHolder> {

    Context context;
    List<CropModel> cropList;
    List<CropModel> fullList;

    public CropAdapter(Context context, List<CropModel> list) {
        this.context = context;
        this.cropList = new ArrayList<>(list);
        this.fullList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_crop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CropModel crop = cropList.get(position);

        holder.tvName.setText(crop.getName());
        holder.tvLocation.setText("Location: " + crop.getLocation());
        holder.tvFarmer.setText("Farmer: " + crop.getFarmer());
        holder.tvPrice.setText("Price: " + crop.getPrice());
        holder.tvQuantity.setText("Qty: " + crop.getQuantity());

        String imageName = crop.getName().toLowerCase().replace(" ", "");

        int imageResId = context.getResources()
                .getIdentifier(imageName, "drawable", context.getPackageName());

        if (imageResId != 0) {
            holder.cropImage.setImageResource(imageResId);
        } else {
            holder.cropImage.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public void filter(String text, String category) {
        cropList.clear();

        for (CropModel crop : fullList) {

            boolean searchMatch = crop.getName().toLowerCase()
                    .contains(text.toLowerCase());

            boolean categoryMatch = category.equals("All")
                    || crop.getCategory().equalsIgnoreCase(category);

            if (searchMatch && categoryMatch) {
                cropList.add(crop);
            }
        }

        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cropImage;
        TextView tvName, tvLocation, tvFarmer, tvPrice, tvQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cropImage = itemView.findViewById(R.id.cropImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvFarmer = itemView.findViewById(R.id.tvFarmer);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
        }
    }
}
