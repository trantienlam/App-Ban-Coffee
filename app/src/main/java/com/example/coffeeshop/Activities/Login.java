package com.example.coffeeshop.Activities;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.coffeeshop.Contains.UserIsLoggedIn;
import com.example.coffeeshop.R;
import com.example.coffeeshop.Utils.DatabaseHandler;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    Button signIn;
    Button signUp;
    TextInputEditText username;
    TextInputEditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DatabaseHandler db = new DatabaseHandler(this);
        db.addDefaultUser();
        signIn = findViewById(R.id.btn_signIn) ;
        signUp = findViewById(R.id.btn_signUp);
        username = findViewById(R.id.edt_username);
        password = findViewById(R.id.edt_password);
        if(UserIsLoggedIn.isLogin == true) // still login
        {
            Intent i = new Intent(Login.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,SignUp.class);
                startActivity(i);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                {
                    Toast.makeText(Login.this, "Please enter username & password", Toast.LENGTH_SHORT).show();
                    username.requestFocus();
                    return;
                }
                if(isAuthentication(username.getText().toString(),password.getText().toString()))
                {
                    Intent i = new Intent(Login.this,MainActivity.class);
                    startActivity(i);
                    finish();

                }
                else
                {
                    Toast.makeText(Login.this, "Login failed, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private boolean isAuthentication(String username, String password) {
        DatabaseHandler db = new DatabaseHandler(this);
        int id = db.getUserIdLogged(username,password);
        if(id !=  -1)
        {
            UserIsLoggedIn.UserIdLogged = id;
            UserIsLoggedIn.UsernameLogged = username;
            UserIsLoggedIn.isLogin = true;
            return true;
        }
        UserIsLoggedIn.isLogin = false;
        UserIsLoggedIn.UsernameLogged = "";
        UserIsLoggedIn.UserIdLogged = -1;
        return false;
    }
}