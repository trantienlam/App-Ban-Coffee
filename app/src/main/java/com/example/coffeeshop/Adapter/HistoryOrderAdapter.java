package com.example.coffeeshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshop.Activities.DetailHistoryActivity;
import com.example.coffeeshop.DTO.Cart;
import com.example.coffeeshop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HistoryOrderAdapter  extends  RecyclerView.Adapter<HistoryOrderAdapter.ViewHolder> {
    List<Cart> cartList ;
    Context ctx;


    public HistoryOrderAdapter(Context ctx) {
        this.ctx = ctx;
    }
    public void setData(List<Cart> carts)
    {
        this.cartList = carts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        if (cart == null)
            return;
        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String formattedValue = currencyFormatter.format(cart.getTotal_price());
        holder.tvTotal.setText(formattedValue);
        holder.tvAddress.setText("Địa chỉ: "+ cart.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, DetailHistoryActivity.class);
                i.putExtra("id_cart",cart.get_id());
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(cartList != null)
            return  cartList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvAddress;
        TextView tvTotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress = itemView.findViewById(R.id.history_address);
            tvTotal = itemView.findViewById(R.id.history_total);
        }
    }
}
