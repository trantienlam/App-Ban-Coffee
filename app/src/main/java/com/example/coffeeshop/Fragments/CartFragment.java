package com.example.coffeeshop.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeshop.Activities.TransactionActivity;
import com.example.coffeeshop.Contains.UserIsLoggedIn;
import com.example.coffeeshop.DTO.Cart;
import com.example.coffeeshop.Adapter.CartAdapter;
import com.example.coffeeshop.DTO.DetailCart;
import com.example.coffeeshop.Utils.DatabaseHandler;
import com.example.coffeeshop.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class CartFragment extends Fragment implements CartAdapter.ListenerRefesh {

    private static final Double Delivery = 35d;
    private static final Double Taxes = 15d;
    private static final Double Total_Price = Delivery+Taxes;


    public CartFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    TextView tvDelivery;
    TextView tvTaxes ;
    TextView tvTotal;
    Button btnPay;

    @Override
    public void onResume() { //tải thông tin giỏ hàng và tính toán tổng tiền
        super.onResume();
        reload_Pay();
        loadCartDetail();
    }
    RecyclerView rcvCart;
    @Override
    //thiet lap giao dien
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_cart, container, false);
        DatabaseHandler db = new DatabaseHandler(getContext());
        //
        tvDelivery = view.findViewById(R.id.deliveryCharges);
        tvTaxes = view.findViewById(R.id.taxes);
        tvTotal = view.findViewById(R.id.grandTotal);
        btnPay = view.findViewById(R.id.btn_pay);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart match = db.getCartByUserID(UserIsLoggedIn.UserIdLogged);
                if(match.get_id() == null)
                {
                    Toast.makeText(getContext(), "Giỏ hàng trống, vui lòng thêm thứ gì đó", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<DetailCart> detailCartList = db.getAllDetailCartByUser_CartID(UserIsLoggedIn.UserIdLogged,match.get_id());
                if(detailCartList.size() <= 0)
                {
                    Toast.makeText(getContext(), "Giỏ hàng trống, vui lòng thêm thứ gì đó", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(getContext(), TransactionActivity.class);
                i.putExtra("Tổng tiền",tvTotal.getText().toString());
                getContext().startActivity(i);
            }
        });
        reload_Pay();//tính toán tổng tiền bao gồm phí giao hàng và thuế, sau đó định dạng và hiển thị nó.

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvCart = view.findViewById(R.id.rcvCart);
        rcvCart.setLayoutManager(verticalLayoutManager);

        loadCartDetail();

        // Inflate the layout for this fragment
        return view; 
    }
    void loadCartDetail()//tải chi tiết giỏ hàng từ cơ sở dữ liệu và cập nhật giao diện người dùng.
    {
        DatabaseHandler db = new DatabaseHandler(getContext());
        CartAdapter adapter = new CartAdapter(getContext(), CartFragment.this);
        Cart match = db.getCartByUserID(UserIsLoggedIn.UserIdLogged);
        if(match.get_id() == null)
        {
            List<DetailCart> detailCartList = new ArrayList<>(0);
            adapter.setData(detailCartList);
            rcvCart.setAdapter(adapter);
            return;
        }
        List<DetailCart> detailCartList = db.getAllDetailCartByUser_CartID(UserIsLoggedIn.UserIdLogged,match.get_id());
        if(detailCartList.size() <= 0)
        {
            return;
        }
        btnPay.setText("Đặt hàng ("+detailCartList.size()+")");
        adapter.setData(detailCartList);
        rcvCart.setAdapter(adapter);

    }
    private void reload_Pay()//tính toán tổng tiền bao gồm phí giao hàng và thuế, sau đó định dạng và hiển thị nó.
    {
        DatabaseHandler db = new DatabaseHandler(getContext());
        Cart match = db.getCartByUserID(UserIsLoggedIn.UserIdLogged);
        reload_CheckOut();
        tvDelivery.setText(Delivery.toString() + " $");
        tvTaxes.setText(Taxes.toString() + " $");
        double total =  Delivery + Taxes;
        tvTotal.setText(Double.toString(total));
        if(match.get_id() == null)
            return;
        total +=  db.getTotalByUser_Cart(UserIsLoggedIn.UserIdLogged,match.get_id());
        // set tv of grand total

        String tol = Double.toString(total) + " $";
        Locale locale = new Locale("en", "US"); // or you can use Locale.getDefault() for the default locale
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String formattedValue = currencyFormatter.format(total);
        tvTotal.setText(formattedValue);

    }

    public void reload_CheckOut()//cập nhật văn bản của nút "Thanh toán" dựa trên số lượng mặt hàng trong giỏ hàng.
    {
        DatabaseHandler db = new DatabaseHandler(getContext());
        Cart match = db.getCartByUserID(UserIsLoggedIn.UserIdLogged);
        btnPay.setText("CHECK OUT (0)");
        if(match.get_id() == null)
        {
            return ;
        }
        List<DetailCart> detailCartList = db.getAllDetailCartByUser_CartID(UserIsLoggedIn.UserIdLogged,match.get_id());
        if(detailCartList.size() > 0)
            btnPay.setText("CHECK OUT ("+detailCartList.size()+")");
    }
    @Override
    public void refreshActivity() {
        reload_Pay();
    }// lam moi thong tin thanh toan
}