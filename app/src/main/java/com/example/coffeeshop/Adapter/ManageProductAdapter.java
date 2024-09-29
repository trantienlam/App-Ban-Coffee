package com.example.coffeeshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeeshop.Activities.DetailMangeProductActivity;
import com.example.coffeeshop.DTO.Product;
import com.example.coffeeshop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ManageProductAdapter extends RecyclerView.Adapter<ManageProductAdapter.ViewHolder> {
//Lớp ManageProductAdapter là một adapter cho RecyclerView, được sử dụng để hiển thị danh sách sản phẩm trong giao diện quản lý.
    private List<Product> productList;
    private Context ctx;

    public ManageProductAdapter(Context ctx) {
        this.ctx = ctx;
    }
    public void setData(List<Product> list)
    {
        this.productList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ManageProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    //thiết lập sự kiện khi người dùng nhấn vào sản phẩm để mở DetailMangeProductActivity.
    public void onBindViewHolder(@NonNull ManageProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);
        if(product == null)
            return;

        holder.tvName.setText(product.getName());
        Locale locale = new Locale("en", "US"); // or you can use Locale.getDefault() for the default locale
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String formattedValue = currencyFormatter.format(product.getPrice());

        Glide
                .with(ctx)
                .load(product.getImageBook()).centerCrop().placeholder(R.drawable.icon_launcher)
                .into(holder.img);
        holder.tvPrice.setText(formattedValue);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, DetailMangeProductActivity.class);
                i.putExtra("id",product.get_id());
                ctx.startActivity(i);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(productList != null)
            return productList.size();
        return 0;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvPrice;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {// giữ các view của từng item trong danh sách.
            super(itemView);
            tvName = itemView.findViewById(R.id.name_of_product_admin);
            tvPrice = itemView.findViewById(R.id.price_of_product_admin);
            img = itemView.findViewById(R.id.admin_img_product);

        }
    }
}
