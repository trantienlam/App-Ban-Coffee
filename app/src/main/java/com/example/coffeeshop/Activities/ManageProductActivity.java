package com.example.coffeeshop.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.coffeeshop.DTO.Product;
import com.example.coffeeshop.R;
import com.example.coffeeshop.Utils.DatabaseHandler;
import com.example.coffeeshop.Adapter.ManageProductAdapter;

import java.util.List;

public class ManageProductActivity extends AppCompatActivity {
    //hiển thị danh sách các sản phẩm dưới dạng danh sách cuộn
    RecyclerView rcv ;
    ManageProductAdapter adapter ;
    ImageButton btnBack;
    ImageButton btnAdd;


    @Override
    protected void onResume() {
        super.onResume();
        update();
    }
     void update()
     {
         DatabaseHandler db = new DatabaseHandler(this);
         List<Product> productList=  db.getAllProducts();
         adapter.setData(productList);//capnhat
         rcv.setAdapter(adapter);
     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);

//ánh xạ

        rcv = findViewById(R.id.rcvManage);
        btnBack = findViewById(R.id.btn_back);
        btnAdd = findViewById(R.id.btn_add);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        DatabaseHandler db = new DatabaseHandler(this);
        List<Product> productList=  db.getAllProducts();

        adapter = new ManageProductAdapter(this);
        adapter.setData(productList);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rcv.setLayoutManager(verticalLayoutManager);
        rcv.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageProductActivity.this,AddProductManageActivity.class);
                startActivity(i);
            }
        });

    }
}