package com.example.coffeeshop.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeeshop.Contains.UserIsLoggedIn;
import com.example.coffeeshop.DTO.Product;
import com.example.coffeeshop.Activities.DetailProduct;
import com.example.coffeeshop.R;
import com.example.coffeeshop.Utils.DatabaseHandler;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {
    private List<Product> productList;
    private List<Product> productList_Old;

    Context context;
    public ProductAdapter(Context t)
    {
        context = t;
    }
    public void setData(List<Product> list)
    {
        this.productList = list;
        this.productList_Old = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    //Phương thức này gắn dữ liệu của sản phẩm vào các view của ProductViewHolder
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        if(product == null)
        {
            return;
        }
        holder.nameOfProduct.setText(product.getName());
        Glide
                .with(context)
                .load(product.getImageBook()).centerCrop().placeholder(R.drawable.icon_launcher)
                .into(holder.imgProduct);
        Locale locale = new Locale("en", "US"); // or you can use Locale.getDefault() for the default locale
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String formattedValue = currencyFormatter.format(product.getPrice());
        holder.priceOfProduct.setText(formattedValue);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailProduct.class);
                i.putExtra("id",Integer.toString(product.get_id()) );
                context.startActivity(i);
            }
        });
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlphaAnimation btnClick = new AlphaAnimation(1F,0.9F);
                view.startAnimation(btnClick);
                DatabaseHandler db = new DatabaseHandler(view.getContext());
                db.AddToCart(product.get_id(), UserIsLoggedIn.UserIdLogged);
            }
        });
    }
    @Override
    //so luong theo danh sach
    public int getItemCount() {
        if(productList != null)
            return productList.size();
        return 0;
    }

    @Override
    //loc ds sp dua tren tim kiem
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if(search.isEmpty())
                {
                    productList=  productList_Old;
                }
                else
                {
                    List<Product> productsSearch = new ArrayList<Product>();
                    for (Product cont : productList_Old)
                    {
                        if(cont.getName().toLowerCase().contains(search.toLowerCase()) )
                        {
                            productsSearch.add(cont);
                        }
                    }
                    productList = productsSearch;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = productList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productList = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public Filter getFilterByIDCate() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                int idCate = Integer.parseInt((String) charSequence);
                if(idCate == 0)
                {
                    productList = productList_Old;
                }
                else
                {
                    List<Product> new_products = new ArrayList<>();
                    for (Product i : productList_Old)
                    {
                        if(i.get_id_cate() == idCate)
                        {
                            new_products.add(i);
                        }
                    }
                    productList = new_products;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productList = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private  TextView nameOfProduct;
        private TextView priceOfProduct;
        private Button btnAdd;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            imgProduct = itemView.findViewById(R.id.image);
            nameOfProduct = itemView.findViewById(R.id.nameOfCoffee);
            priceOfProduct = itemView.findViewById(R.id.priceOfProduct);
            btnAdd = itemView.findViewById(R.id.btn_add);
        }
    }

}

