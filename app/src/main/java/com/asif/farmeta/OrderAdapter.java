package com.asif.farmeta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<OrderModel> orderList;

    public OrderAdapter(Context context, List<OrderModel> orderList){
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        OrderModel order = orderList.get(position);
        holder.tv1.setText(order.getCropName() + " | Qty: " + order.getQuantity());
        holder.tv2.setText("Price: " + order.getPrice() + " | Farmer: " + order.getFarmerName());
    }

    @Override
    public int getItemCount(){ return orderList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv1, tv2;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tv1 = itemView.findViewById(android.R.id.text1);
            tv2 = itemView.findViewById(android.R.id.text2);
        }
    }
}
