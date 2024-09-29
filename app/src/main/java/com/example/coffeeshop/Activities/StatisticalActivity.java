package com.example.coffeeshop.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.coffeeshop.Adapter.HistoryOrderAdapter;
import com.example.coffeeshop.Contains.UserIsLoggedIn;
import com.example.coffeeshop.DTO.Cart;
import com.example.coffeeshop.R;
import com.example.coffeeshop.Utils.DatabaseHandler;

import java.util.List;

public class StatisticalActivity extends AppCompatActivity {
    RecyclerView rcvHistory;// dach sach don hang
    HistoryOrderAdapter adapter;//để quản lý dữ liệu và hiển thị trong RecyclerView.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);

        rcvHistory = findViewById(R.id.rcvHistory);
        adapter = new HistoryOrderAdapter(this);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvHistory.setLayoutManager(verticalLayoutManager);
        DatabaseHandler db = new DatabaseHandler(this);
        List<Cart> cartList = db.getAllCarts();//danh sach don hang da dat
        adapter.setData(cartList);
        rcvHistory.setAdapter(adapter);
    }
}