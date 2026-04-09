package com.asif.farmeta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FertilizerAdapter extends RecyclerView.Adapter<FertilizerAdapter.ViewHolder> {

    Context context;
    ArrayList<FertilizerModel> list;

    public FertilizerAdapter(Context context, ArrayList<FertilizerModel> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCrop, tvLand, tvNPK, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCrop = itemView.findViewById(R.id.tvCrop);
            tvLand = itemView.findViewById(R.id.tvLand);
            tvNPK = itemView.findViewById(R.id.tvNPK);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fertilizer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FertilizerModel m = list.get(position);
        holder.tvCrop.setText(" Crop: " + m.crop);
        holder.tvLand.setText("📏 Land: " + m.land + " " + m.unit);
        holder.tvNPK.setText("🧪 NPK → N:" + m.n + " P:" + m.p + " K:" + m.k);
        holder.tvDate.setText("📅 " + m.date);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}