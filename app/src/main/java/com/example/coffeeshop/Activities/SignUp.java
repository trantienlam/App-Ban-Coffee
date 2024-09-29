package com.example.coffeeshop.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.coffeeshop.Contains.UserIsLoggedIn;
import com.example.coffeeshop.DTO.User;
import com.example.coffeeshop.R;
import com.example.coffeeshop.Utils.DatabaseHandler;
import com.google.android.material.textfield.TextInputEditText;

public class SignUp extends AppCompatActivity {
    ImageButton back;
    Button btn_confirm;

    TextInputEditText edt_fullname;
    TextInputEditText edt_phonenumber;
    TextInputEditText edt_password;
    TextInputEditText edt_confirmpassword;
    TextInputEditText edt_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        back = (ImageButton) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_confirm = findViewById(R.id.btn_Confirm);
        edt_fullname = findViewById(R.id.edt_fullName);
        edt_phonenumber = findViewById(R.id.edt_phoneNumber);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        edt_confirmpassword = findViewById(R.id.edt_confirmPassword);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }
    private void Register()
    {
        String fullname=  edt_fullname.getText().toString();
        String phonenumber=  edt_phonenumber.getText().toString();
        String username=  edt_username.getText().toString();
        String password=  edt_password.getText().toString();
        String confirm=  edt_confirmpassword.getText().toString();
        if(fullname.isEmpty() || phonenumber.isEmpty() || username.isEmpty() || password.isEmpty()|| confirm.isEmpty())
        {
            Toast.makeText(SignUp.this, "Please enter all field", Toast.LENGTH_SHORT).show();
            return;
        }
        if(confirm.equals(password) == false)
        {
            Toast.makeText(SignUp.this, "Confirm password do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        User newUser = new User(0,username,password,fullname,phonenumber);
        DatabaseHandler db = new DatabaseHandler(this);
        db.Register(newUser);
        int id = db.getUserIdLogged(username,password);
        if(id !=  -1)
        {
            UserIsLoggedIn.UserIdLogged = id;
            UserIsLoggedIn.UsernameLogged = username;
            UserIsLoggedIn.isLogin = true;
            Intent i = new Intent(SignUp.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}