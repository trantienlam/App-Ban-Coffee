package com.example.coffeeshop.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;

import com.example.coffeeshop.DTO.Category;
import com.example.coffeeshop.R;
import com.example.coffeeshop.Custom.VerticalTextView;

import java.util.List;

public class ListViewCategoryAdapter extends ArrayAdapter<Category> {
    //Lớp ListViewCategoryAdapter là một adapter tuỳ chỉnh cho ListView, sử dụng để hiển thị danh sách các Category
    private final Activity context;
    private final List<Category> categoryList;
    Listener listener;

    public ListViewCategoryAdapter(Activity context,  List<Category> categoryList,Listener listener) {
        super(context, R.layout.button_rotate_vertical,categoryList);
        this.listener = listener;
        this.context=context;
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();//
        View rowView=inflater.inflate(R.layout.button_rotate_vertical, null,true);
        VerticalTextView title = (VerticalTextView) rowView.findViewById(R.id.button_vertical);
        Category category = categoryList.get(position);
        title.setText(category.getNameCategory());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemListener(category);

                notifyDataSetChanged();
            }
        });
        return rowView;

    };
    public interface  Listener{
        void OnItemListener( Category category);
    }
}
