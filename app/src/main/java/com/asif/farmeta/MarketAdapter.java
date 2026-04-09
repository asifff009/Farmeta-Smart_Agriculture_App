package com.asif.farmeta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder>{

    Context context;
    ArrayList<MarketModel> list;

    public MarketAdapter(Context context, ArrayList<MarketModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item_market,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i){
        MarketModel m = list.get(i);

        h.crop.setText(m.getCrop());
        h.price.setText("Price: " + m.getPrice());
        h.district.setText(m.getDistrict());
        h.date.setText(m.getDate());
    }

    @Override
    public int getItemCount(){ return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView crop, price, district, date;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            crop = itemView.findViewById(R.id.crop);
            price = itemView.findViewById(R.id.price);
            district = itemView.findViewById(R.id.district);
            date = itemView.findViewById(R.id.date);
        }
    }
}