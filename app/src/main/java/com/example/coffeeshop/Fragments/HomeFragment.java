package com.example.coffeeshop.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.coffeeshop.Activities.Login;
import com.example.coffeeshop.Activities.ManageProductActivity;
import com.example.coffeeshop.Activities.StatisticalActivity;
import com.example.coffeeshop.Contains.UserIsLoggedIn;
import com.example.coffeeshop.Adapter.ListViewCategoryAdapter;
import com.example.coffeeshop.DTO.Category;
import com.example.coffeeshop.Utils.DatabaseHandler;
import com.example.coffeeshop.DTO.Product;
import com.example.coffeeshop.Adapter.ProductAdapter;
import com.example.coffeeshop.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements ListViewCategoryAdapter.Listener {
    TextInputEditText passEdt, nameEdt, phoneEdt;
    TextInputEditText newPassEdt;
    TextInputEditText enterPassEdt;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    // các sản phẩm được tải từ cơ sở dữ liệu và cập nhật vào Adapter.
    public void onResume() {
        super.onResume();
        DatabaseHandler db = new DatabaseHandler(getContext());
        products =db.getAllProducts();

        adapter.setData(products);
    }
    void dialog(){//hien thi tuy chinh de doi mat khau
        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.custom_dialog_change_pass);
        Drawable customBackground  = ContextCompat.getDrawable(requireActivity(), R.drawable.dialog_backgroud);
        dialog.getWindow().setBackgroundDrawable(customBackground);

         passEdt = dialog.findViewById(R.id.passEdt);
         newPassEdt = dialog.findViewById(R.id.newPassEdt);
         enterPassEdt = dialog.findViewById(R.id.enterPassEdt);

        Button acceptBtn = dialog.findViewById(R.id.acceptBtn);
        Button cancel = dialog.findViewById(R.id.cancelBtn);

        acceptBtn.setOnClickListener(v -> {
            updatePass();
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }
    private void updatePass(){
        boolean check = check();
        if (check){
            DatabaseHandler db = new DatabaseHandler(getContext());
            String account = passEdt.getText().toString();
            String enterPass = enterPassEdt.getText().toString();
            boolean update = db.updatePassword(account, enterPass);
            if (update)
            {
                showToast("Thay đổi mật khẩu thành công");
            }else {
                showToast("Thay đổi mật khẩu thất bại");
            }
        }else {
            showToast("Lỗi");
        }
    }
    private boolean check(){
        if (passEdt.getText().toString().trim().isEmpty()) {
            showToast("Nhập tài khoản");
            return false;
        }
        else if (newPassEdt.getText().toString().trim().length() <= 6) {
            showToast("Mật khẩu phải có độ dài trên 6 ký tự");
            return false;
        }else if (enterPassEdt.getText().toString().trim().isEmpty()) {
            showToast("Nhập lại mật khẩu");
            return false;
        } else if (!newPassEdt.getText().toString().equals(enterPassEdt.getText().toString())) {
            showToast("Mật khẩu nhập lại không khớp với mật khẩu đã nhập trước đó!");
            return false;
        } else {
            return true;
        }

    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
// cap nhat thong tin
    void dialogProfile(){
        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.custom_dialog_change_profile);
        Drawable customBackground  = ContextCompat.getDrawable(requireActivity(), R.drawable.dialog_backgroud);
        dialog.getWindow().setBackgroundDrawable(customBackground);

        nameEdt = dialog.findViewById(R.id.nameEdt);
        phoneEdt = dialog.findViewById(R.id.phoneEdt);

        Button acceptBtn = dialog.findViewById(R.id.acceptBtn);
        Button cancel = dialog.findViewById(R.id.cancelBtn);

        acceptBtn.setOnClickListener(v -> {
            String name = nameEdt.getText().toString();
            String sdt = phoneEdt.getText().toString();

            if (!name.isEmpty() && !sdt.isEmpty()){
                DatabaseHandler db = new DatabaseHandler(getContext());
                boolean update = db.updateLocationAndPhone(UserIsLoggedIn.UserIdLogged, name, sdt);
                if (update){
                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        DatabaseHandler db = new DatabaseHandler(getContext());
        Cursor cursor = db.getAccountData(UserIsLoggedIn.UserIdLogged);
        if (cursor != null) {

            if (cursor.moveToFirst()) {
               @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String sdt = cursor.getString(cursor.getColumnIndex("phone_number"));

                nameEdt.setText(name);
                phoneEdt.setText(sdt);
            }
            cursor.close();
        }
        dialog.show();
    }

    List<Product> products = new ArrayList<>();
    ProductAdapter adapter = new ProductAdapter(getContext());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment_home, container, false);
        products = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(getContext());
        products =db.getAllProducts();


        LinearLayoutManager verticalLayoutManager = new GridLayoutManager(getActivity(), 2);
        RecyclerView rcvHome = view.findViewById(R.id.rcvHome);
        rcvHome.setLayoutManager(verticalLayoutManager);

        adapter = new ProductAdapter(getContext());
        adapter.setData(products);
        rcvHome.setAdapter(adapter);
        Category All = new Category("ALL",0);
        List<Category> categories = db.getAllCategories();
        categories.add(0,All);
        // List view chứa thong tin từng loại
        ListViewCategoryAdapter adapter_cate = new ListViewCategoryAdapter(getActivity(), categories, HomeFragment.this);
        ListView list = (ListView) view.findViewById(R.id.list_category);
        list.setAdapter(adapter_cate);

        //search
        EditText search = view.findViewById(R.id.edt_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            //Mỗi khi người dùng nhập dữ liệu vào ô tìm kiếm, adapter.getFilter().filter(charSequence); được gọi để lọc danh sách sản phẩm dựa trên từ khóa nhập vào.
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ImageButton btnProfile = view.findViewById(R.id.btn_profile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), btnProfile);
                // Inflating popup menu from popup_menu.xml file

                popupMenu.getMenuInflater().inflate(R.menu.control_account_menu, popupMenu.getMenu());
                int  id_mange_product = 0403;
                int  id_mange_statistical = 0404;// ql thống kê
                if(UserIsLoggedIn.UsernameLogged.equals("admin"))
                {
                    popupMenu.getMenu().add(Menu.NONE,id_mange_product,0,"Quản lý sản phẩm");
                    popupMenu.getMenu().add(Menu.NONE,id_mange_statistical,0,"Thống kê");
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == id_mange_product)
                        {
                            Intent i = new Intent(getContext(), ManageProductActivity.class);
                            startActivity(i);
                        }
                        if(menuItem.getItemId() == id_mange_statistical)
                        {
                            Intent i = new Intent(getContext(), StatisticalActivity.class);
                            startActivity(i);
                        }
                        else if(menuItem.getItemId() == R.id.signout)
                        {
                            UserIsLoggedIn.signOut();
                            UserIsLoggedIn.isLogin = false ;
                            Intent i = new Intent(getContext(),Login.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                        else if(menuItem.getItemId() == R.id.profile)
                        {
                            dialogProfile();
                        }
                        else if(menuItem.getItemId() == R.id.changepassword)
                        {
                            dialog();
                        }
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });
        return view;
    }



    @Override
    public void OnItemListener(Category category) {
        adapter.getFilterByIDCate().filter(Integer.toString(category.get_id()));
    }


}