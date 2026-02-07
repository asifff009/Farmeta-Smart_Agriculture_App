package com.asif.farmeta;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.ViewHolder> {

    private Context context;
    private List<CropModel> cropList;
    private boolean isBuyerMode;

    public CropAdapter(Context context, List<CropModel> cropList) {
        this.context = context;
        this.cropList = cropList;
        this.isBuyerMode = false;
    }

    public CropAdapter(Context context, List<CropModel> cropList, boolean isBuyerMode) {
        this.context = context;
        this.cropList = cropList;
        this.isBuyerMode = isBuyerMode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CropModel crop = cropList.get(position);
        holder.tv1.setText(crop.getCropName() + " | " + crop.getStatus());
        holder.tv2.setText("Yield: " + crop.getExpectedYield() + " kg, Start: " + crop.getStartDate());

        if (isBuyerMode) {
            holder.itemView.setOnClickListener(v -> showOrderDialog(crop));
        }
    }

    private void showOrderDialog(CropModel crop) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Place Order for " + crop.getCropName());

        final EditText input = new EditText(context);
        input.setHint("Quantity (kg)");
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        builder.setPositiveButton("Place Order", (dialog, which) -> {
            String quantity = input.getText().toString().trim();
            if (quantity.isEmpty()) {
                Toast.makeText(context, "Enter quantity", Toast.LENGTH_SHORT).show();
                return;
            }
            placeOrder(crop, quantity);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void placeOrder(CropModel crop, String quantity) {
        String BUYER_ID = "1"; // TODO: get actual buyer_id from session
        String PLACE_ORDER_URL = "http://10.0.2.2/farmeta_api/place_order.php";

        StringRequest request = new StringRequest(Request.Method.POST, PLACE_ORDER_URL,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.getString("status").equals("success")) {
                            Toast.makeText(context, "Order placed successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Order failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(context, "Error placing order", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("buyer_id", BUYER_ID);
                map.put("farmer_id", crop.getFarmerId());
                map.put("crop_id", crop.getCropId()); // now using proper crop_id
                map.put("quantity", quantity);
                map.put("price", "100"); // example
                return map;
            }
        };

        Volley.newRequestQueue(context).add(request);
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(android.R.id.text1);
            tv2 = itemView.findViewById(android.R.id.text2);
        }
    }
}
