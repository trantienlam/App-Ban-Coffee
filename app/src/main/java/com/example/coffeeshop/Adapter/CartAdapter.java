package com.example.coffeeshop.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeeshop.Activities.DetailProduct;
import com.example.coffeeshop.DTO.Cart;
import com.example.coffeeshop.DTO.Category;
import com.example.coffeeshop.DTO.DetailCart;
import com.example.coffeeshop.DTO.Product;
import com.example.coffeeshop.R;
import com.example.coffeeshop.Utils.DatabaseHandler;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface ListenerRefesh {
        public void refreshActivity();
    }
    private List<DetailCart> cartList;
    Context context;
    ListenerRefesh listenerRefesh;
    public CartAdapter(Context context, ListenerRefesh listenerRefesh) {
        this.listenerRefesh = listenerRefesh;
        this.context = context;
    }
    public CartAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<DetailCart> list)
    {
        this.cartList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_cart,parent,false);


        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        DetailCart detailCart = cartList.get(position);
        DatabaseHandler db = new DatabaseHandler(context);
        Cart cart = db.getCartByID(detailCart.get_id_cart());
        if(detailCart == null)
        {
            return;
        }
        holder.edt_Quantity.setText(Integer.toString(detailCart.getQuantity()) );
        Product pro =db.getProductByID(detailCart.get_id_product());
        Glide
                .with(context)
                .load(pro.getImageBook()).centerCrop().placeholder(R.drawable.icon_launcher)
                .into(holder.img);
        holder.name.setText(pro.getName());
        Locale locale = new Locale("en", "US"); // or you can use Locale.getDefault() for the default locale
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String formattedValue = currencyFormatter.format(detailCart.getPrice());
        holder.price.setText(formattedValue );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailProduct.class);


                i.putExtra("id",Integer.toString(pro.get_id()) );
                context.startActivity(i);
            }
        });
        Category cate = db.getCategoryByID(pro.get_id_cate());
        holder.subtitle.setText(cate.getNameCategory());
        //btn increase
        if(cart.getStatus() == 0)
        {

            holder.btn_incr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlphaAnimation btnClick = new AlphaAnimation(1F,0.9F);
                    view.startAnimation(btnClick);
                    int quantity = Integer.parseInt(holder.edt_Quantity.getText().toString());
                    quantity++;
                    detailCart.setQuantity(quantity);
                    holder.edt_Quantity.setText(Integer.toString(quantity));
                    db.updateDetail(detailCart);
                    notifyDataSetChanged();

                }
            });
            // btn decrease
            holder.btn_decr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlphaAnimation btnClick = new AlphaAnimation(1F,0.9F);
                    view.startAnimation(btnClick);
                    int quantity = Integer.parseInt(holder.edt_Quantity.getText().toString());
                    if(quantity <= 1)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Confirm ?");
                        builder.setMessage("Are you sure remove?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteDetail(detailCart);
                                cartList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, cartList.size());
                                //notifyDataSetChanged();
                                listenerRefesh.refreshActivity();
                                // Do nothing, but close the dialog
                                dialog.dismiss();
                                return;

                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing
                                notifyDataSetChanged();

                                dialog.dismiss();
                                return;
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else
                    {
                        quantity--;
                        detailCart.setQuantity(quantity);
                        holder.edt_Quantity.setText(Integer.toString(quantity) );
                        db.updateDetail(detailCart);
                    }
                    notifyDataSetChanged();
                }
            });
            listenerRefesh.refreshActivity();
        }

    }

    @Override
    public int getItemCount() {
        if(cartList != null)
            return cartList.size();
        return 0;
    }


    public class CartViewHolder extends RecyclerView.ViewHolder{
        EditText edt_Quantity;
        ImageView img;
        TextView name;
        TextView price;
        TextView subtitle;

        Button btn_incr;
        Button btn_decr;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image_of_product_cart);
            name = itemView.findViewById(R.id.name_of_product_cart);
            price = itemView.findViewById(R.id.price_of_product_cart);
            subtitle = itemView.findViewById(R.id.subtitle);
            edt_Quantity = itemView.findViewById(R.id.input_quantity);
            btn_incr = itemView.findViewById(R.id.btn_plus);
            btn_decr = itemView.findViewById(R.id.btn_minus);
        }
    }
}
