package com.example.coffeeshop.Contains;

import android.content.Context;

public class UserIsLoggedIn {
    public static String UsernameLogged;
    public static int UserIdLogged;
    public static  boolean isLogin;
    public static void signOut()
    {
        Context ctx;
        UsernameLogged = "";
        isLogin  = false;
        UserIdLogged = -1;
    }

}
