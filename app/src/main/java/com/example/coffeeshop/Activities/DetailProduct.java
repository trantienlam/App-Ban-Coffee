package com.example.coffeeshop.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeshop.Contains.UserIsLoggedIn;
import com.example.coffeeshop.Services.DrawableHandler;
import com.example.coffeeshop.Utils.DatabaseHandler;
import com.example.coffeeshop.DTO.Product;
import com.example.coffeeshop.R;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        Intent i = this.getIntent();
        int id_product = -1;
        if(i!= null)
        {
            id_product = Integer.parseInt(i.getStringExtra("id") ) ;

            DatabaseHandler db = new DatabaseHandler(this);
            Product match =db.getProductByID(id_product);
            ImageView imgView = findViewById(R.id.image_of_product);
            byte[] imageByteArray = match.getImageBook();
            if (imageByteArray != null && imageByteArray.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
                imgView.setImageBitmap(bitmap);
            }
            TextView tvPrice = findViewById(R.id.price_of_product);
            TextView tvDesc = findViewById(R.id.desc_of_product);
            TextView tvName = findViewById(R.id.name_of_product);
            ImageButton btnBack = findViewById(R.id.btn_back);
            ImageButton btnFavourite = findViewById(R.id.btn_heart);
            Button btnBuy = findViewById(R.id.btn_buy);
            if(match.isFavourite() == true)
            {
                btnFavourite.setImageResource(R.drawable.icons8_favorite_50_active);
            }
            btnFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(match.isFavourite())
                    {
                        btnFavourite.setImageResource(R.drawable.icons8_favorite_50_no_active);
                        match.setFavourite(false);
                    }
                    else
                    {
                        btnFavourite.setImageResource(R.drawable.icons8_favorite_50_active);
                        match.setFavourite(true);

                    }
                    db.updateProduct(match);
                }
            });
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlphaAnimation btnClick = new AlphaAnimation(1F,0.9F);
                    view.startAnimation(btnClick);
                    finish();
                }
            });
            Locale locale = new Locale("en", "US"); // or you can use Locale.getDefault() for the default locale
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
            String formattedValue = currencyFormatter.format(match.getPrice());
            tvPrice.setText(formattedValue);
            tvName.setText(match.getName());
            tvDesc.setText(match.getDescription());
            // even add to cart
            int finalId_product = id_product;
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlphaAnimation btnClick = new AlphaAnimation(1F,0.9F);
                    view.startAnimation(btnClick);
                    DatabaseHandler db = new DatabaseHandler(view.getContext());
                    Product match =db.getProductByID(finalId_product);
                    db.AddToCart(match.get_id(), UserIsLoggedIn.UserIdLogged);
                }
            });

        }
    }
}