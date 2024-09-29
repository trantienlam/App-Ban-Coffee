package com.example.coffeeshop.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coffeeshop.DTO.Category;
import com.example.coffeeshop.DTO.Product;
import com.example.coffeeshop.R;
import com.example.coffeeshop.Utils.DatabaseHandler;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class AddProductManageActivity extends AppCompatActivity {

    Spinner dropdownCate;
    TextInputEditText edt_name;
    TextInputEditText edt_price;
    TextInputEditText edt_desc;
    private byte[] imageByteArray;
    private static final int PICK_IMAGE = 1;
    ImageView edt_img;
    RelativeLayout pickimage;
    Button btn_Add;
    ImageButton btnBack;
    Product product = new Product();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_manage);
// ánh xạ
        dropdownCate = findViewById(R.id.dropdown_cate); // chọn danh mục sản phẩm
        edt_name= findViewById(R.id.edt_NameProduct);
        edt_price = findViewById(R.id.edt_PriceProduct);
        edt_desc = findViewById(R.id.edt_DescProduct);
        edt_img = findViewById(R.id.imageBook);
        pickimage = findViewById(R.id.pickimage);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        DropdownCate(); // goi pt khỏi tạo với các danh mục sp

        pickimage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

 // Khi người dùng nhấn vào nút "Thêm Sản Phẩm", phương thức addProduct() sẽ được gọi với dữ liệu ảnh đã được chọn (biến imageByteArray)
        btn_Add = findViewById(R.id.btn_addProduct);
        btn_Add.setOnClickListener(v -> {
            addProduct(imageByteArray);
        });
    }

    void addProduct(byte[] image){
        String name = edt_name.getText().toString();
        String price = edt_price.getText().toString();
        String desc = edt_desc.getText().toString();

        if(name.isEmpty() || price.isEmpty() || desc.isEmpty())
        {
            Toast.makeText(AddProductManageActivity.this, "Please enter all field", Toast.LENGTH_SHORT).show();
            return;
        }
        // thiet lap cac thuoc tinh
        product.setName(name);
        product.setPrice(Double.parseDouble(price));
        product.setDescription(desc);
        product.setFavourite(false);
        product.setImageBook(image);
        DatabaseHandler db = new DatabaseHandler(AddProductManageActivity.this);
        db.addProduct(product);
        finish();
    }
    @Override
    //xu ly ket qua hinh anh
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();//trả về URI của ảnh được chọn
            edt_img.setImageURI(selectedImage);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);// để nén ảnh dưới định dạng JPEG
                imageByteArray = stream.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    void DropdownCate()//khoi tao danh sach tha xuong voi cac danh muc tu csdl
    {
        DatabaseHandler db = new DatabaseHandler(this);
        List<Category> categoryList = db.getAllCategories();
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_custom,categoryList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropdownCate.setAdapter(adapter);
//click trả về icon
        dropdownCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedHandler(parent,view,position,id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    //thiet lap id cho doi tuong product
    private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        Category category = (Category) adapter.getItem(position);
        product.set_id_cate(category.get_id());
    }
}