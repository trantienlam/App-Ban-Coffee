package com.example.coffeeshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshop.DTO.Category;
import com.example.coffeeshop.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    List<Category> categories;

    public CategoryAdapter(Context context) {
        this.context = context;
    }
    public  void setData(List<Category> l)
    {
        this.categories = l;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_item,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        if (category == null)
        {
            return;
        }
        holder.tvNameCategory.setText(category.getNameCategory());

        ProductAdapter adapter = new ProductAdapter(context);
       // adapter.setData(category.getProducts());

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        holder.rcvProduct.setNestedScrollingEnabled(false);
        holder.rcvProduct.setFocusable(false);
        holder.rcvProduct.setLayoutManager(horizontalLayoutManager);
        holder.rcvProduct.setAdapter(adapter);


    }

    @Override
    public int getItemCount() {
        if(categories != null)
            return categories.size();
        return 0;
    }

    public class CategoryViewHolder   extends RecyclerView.ViewHolder{
        private TextView tvNameCategory;
        private RecyclerView rcvProduct;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameCategory = itemView.findViewById(R.id.category_title);
            rcvProduct = itemView.findViewById(R.id.rcvProducts);
        }
    }
}
