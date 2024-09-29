package com.example.coffeeshop.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coffeeshop.Contains.UserIsLoggedIn;
import com.example.coffeeshop.DTO.Cart;
import com.example.coffeeshop.R;
import com.example.coffeeshop.Utils.DatabaseHandler;
import com.example.coffeeshop.Adapter.HistoryOrderAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    //ARG_PARAM1 và ARG_PARAM2 là các tham số để khởi tạo Fragment.
    //mParam1 và mParam2 là các biến lưu trữ giá trị của tham số được truyền vào.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();//bundle luu tru cac tham so
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    RecyclerView rcvHistory;
    HistoryOrderAdapter adapter;//quản lý dữ liệu lịch sử đơn hàng.
    @Override
    //Nếu có các tham số được truyền vào, chúng sẽ được lấy và lưu trữ trong các biến mParam1 và mParam2.
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate tải layout tu xml
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        rcvHistory = view.findViewById(R.id.rcvHistory);
        adapter = new HistoryOrderAdapter(getContext());
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvHistory.setLayoutManager(verticalLayoutManager);
        DatabaseHandler db = new DatabaseHandler(getContext());
        //getAllCartsByUser_Ordered lấy danh sách các đơn hàng đã được đặt bởi người dùng.
        List<Cart> cartList = db.getAllCartsByUser_Ordered(UserIsLoggedIn.UserIdLogged);
        adapter.setData(cartList);
        rcvHistory.setAdapter(adapter);

        return view;
    }
}