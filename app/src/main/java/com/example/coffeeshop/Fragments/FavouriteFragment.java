package com.example.coffeeshop.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coffeeshop.Utils.DatabaseHandler;
import com.example.coffeeshop.DTO.Product;
import com.example.coffeeshop.Adapter.ProductAdapter;
import com.example.coffeeshop.R;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {



    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshScreen();//lm moi danh sach yeu thich
    }
    void refreshScreen()
    {
        DatabaseHandler db = new DatabaseHandler(getContext());
        List<Product> products = new ArrayList<>();
        products =db.getAlProductsIsFavourite();//Lấy danh sách các sản phẩm yêu thích từ cơ sở dữ liệu bằng phương thức getAlProductsIsFavourite().
        adapter.setData(products);
    }
    RecyclerView rcvCategories;//hiển thị danh sách yêu thích
    ProductAdapter adapter;//quan lý dữ liệu sản phẩm
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment_favourtie, container, false);//tải layout từ xml
        DatabaseHandler db = new DatabaseHandler(getContext());
        List<Product> products = new ArrayList<>();
        products =db.getAlProductsIsFavourite();

        LinearLayoutManager verticalLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcvCategories = view.findViewById(R.id.rcvFavourite);
        rcvCategories.setLayoutManager(verticalLayoutManager);
        adapter = new ProductAdapter(getContext());
        adapter.setData(products);//cập nhật dữ liệu
        rcvCategories.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
}