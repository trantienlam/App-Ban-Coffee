package com.example.coffeeshop.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeshop.Contains.UserIsLoggedIn;
import com.example.coffeeshop.DTO.Cart;
import com.example.coffeeshop.DTO.User;
import com.example.coffeeshop.R;
import com.example.coffeeshop.Utils.DatabaseHandler;
import com.google.android.material.textfield.TextInputEditText;

public class TransactionActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnOrder;
    TextView tvTotal;
    TextInputEditText phone;
    TextInputEditText email;
    TextInputEditText address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        btnBack = findViewById(R.id.btn_back);
        tvTotal= findViewById(R.id.total);
        btnOrder = findViewById(R.id.btn_placeOrder);
        phone = findViewById(R.id.edt_phone);
        email = findViewById(R.id.edt_email);
        address = findViewById(R.id.edt_address);
//Nhận Intent từ Activity trước đó và lấy giá trị tổng tiền (total) để hiển thị trong TextView (tvTotal).
        Intent i = getIntent();
        if(i != null)
        {
            String total =  i.getStringExtra("total");
            tvTotal.setText(total);
        }
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = email.getText().toString();
                String sPhone = phone.getText().toString();
                String sAddress = address.getText().toString();


                if(sAddress.isEmpty() || sEmail.isEmpty() || sPhone.isEmpty())
                {
                    Toast.makeText(TransactionActivity.this, "Please enter all field", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionActivity.this);
                builder.setTitle("Payment confirmation");
                builder.setMessage("Is the address and phone number confirmed to be correct?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler db = new DatabaseHandler(TransactionActivity.this);
                        db.PlaceOrder(UserIsLoggedIn.UserIdLogged,sAddress);
                        addNotification("Thank you for using our service.");
                        onBackPressed();
                        // Do nothing, but close the dialog
                        dialog.dismiss();
                        return;

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                        return;
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();



            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//Lấy thông tin người dùng từ cơ sở dữ liệu và hiển thị trong TextView và TextInputEditText.
        TextView name = findViewById(R.id.name_user);
        DatabaseHandler db = new DatabaseHandler(this);
        User user =  db.getUserById(UserIsLoggedIn.UserIdLogged);
        name.setText(user.getFull_name());
        phone.setText(user.getPhone_number());


    }
    private void addNotification(String s) {
        //quan li tat ca cac thong bao
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CHANNEL_ID ="channel_notification";// mã định danh cho kênh thông báo
            CharSequence name = "my_channel";//Tên hiển thị của kênh thông báo.
            String Description = "This is my channel";// mô tả kênh thông báo
            int importance = NotificationManager.IMPORTANCE_HIGH;// Mức độ quan trọng của kênh thông báo
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            //mChannel.enableVibration(true);
            //mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notifications_48)// biểu tượng nhỏ cho thông báo
                .setContentTitle("Message from coffee shop")// tiêu đề thông báo
                .setContentText(s);// nội dung văn bản thông báo

        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra("notification",1);
        //putExtra: Thêm một giá trị bổ sung vào Intent để phân biệt rằng nó đến từ thông báo.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //TaskStackBuilder: Được sử dụng để đảm bảo rằng khi người dùng nhấn vào thông báo, ngăn xếp hoạt động thích hợp sẽ được tạo ra.
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);//addNextIntent: Thêm Intent tiếp theo vào ngăn xếp.
        //PendingIntent: Được sử dụng để trì hoãn việc thực hiện Intent cho đến khi thông báo được nhấn.
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(1, builder.build());//notificationManager.notify: Gửi thông báo để hiển thị cho người dùng. Thông báo được xây dựng từ builder và được chỉ định một ID duy nhất (1).
    }
}