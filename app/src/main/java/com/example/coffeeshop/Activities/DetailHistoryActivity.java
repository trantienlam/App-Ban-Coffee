package com.example.coffeeshop.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.coffeeshop.DTO.DetailCart;
import com.example.coffeeshop.R;
import com.example.coffeeshop.Adapter.CartAdapter;
import com.example.coffeeshop.Utils.DatabaseHandler;

import java.util.List;

public class DetailHistoryActivity extends AppCompatActivity  implements CartAdapter.ListenerRefesh{
    RecyclerView rcvDetail;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        rcvDetail = findViewById(R.id.rcvDetailHistory);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        DatabaseHandler db = new DatabaseHandler(this);
        int idCart = -1;;
        Intent i = getIntent();
        if(i!=null)
        {
            idCart = i.getIntExtra("id_cart",-1);
        }
        if(idCart == -1)
            return;
        List<DetailCart> detailCartList = db.getAllDetailCartByCartID(idCart);
        if(detailCartList.size() <= 0)
        {
            return;
        }

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        CartAdapter adapter = new CartAdapter(DetailHistoryActivity.this,DetailHistoryActivity.this);
        adapter.setData(detailCartList);
        rcvDetail.setLayoutManager(verticalLayoutManager);
        rcvDetail.setAdapter(adapter);



    }

    @Override
    public void refreshActivity() {

    }
}