package com.example.coffeeshop.Activities;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.coffeeshop.Fragments.CartFragment;
import com.example.coffeeshop.Fragments.FavouriteFragment;
import com.example.coffeeshop.Fragments.HomeFragment;
import com.example.coffeeshop.Fragments.NotificationFragment;
import com.example.coffeeshop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
     Fragment current = new HomeFragment();
     //Khởi tạo biến current để giữ fragment hiện tại và current_page để giữ id của trang hiện tại.
     Integer current_page = R.id.page_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(current);//tải fragment ban đầu.
        // Khởi tạo và gán biến
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);//Tìm kiếm và gán BottomNavigationView từ layout.
        // Set Home selected
        Intent i = getIntent();
        if (i!= null)
        {
            if(i.getIntExtra("notification",-1) == 1)
            {
                current_page = R.id.page_4;//id cua trang
                loadFragment(new NotificationFragment());
            }
        }
        // sự kiện khởi tạo mặc định
        bottomNavigationView.setSelectedItemId(current_page);
        // Perform item selected listener
        // sự kiện khi nhấn các nút bottom
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch(item.getItemId())
                {
                    case R.id.page_1:
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.page_2:
                        fragment = new CartFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.page_3:
                        fragment = new FavouriteFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.page_4:
                        fragment = new NotificationFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//quản lý các fragment trong giao diện.


        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}