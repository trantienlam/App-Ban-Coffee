package com.example.coffeeshop.DTO;

public class User {
    private int _id;
    private String _username;
    private String _password;
    private  String full_name;
    private  String phone_number;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public User() {
    }

    public User(int _id, String _username, String _password, String full_name, String phone_number) {
        this._id = _id;
        this._username = _username;
        this._password = _password;
        this.full_name = full_name;
        this.phone_number = phone_number;
    }
}
