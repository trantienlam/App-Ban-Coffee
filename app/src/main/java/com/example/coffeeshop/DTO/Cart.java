package com.example.coffeeshop.DTO;

public class Cart {
    private Integer _id;
    private Integer _id_user;
    private Double total_price;
    private String address;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    private int Status;

    public Cart(Integer _id, Integer _id_user, Double total_price, String address, int status) {
        this._id = _id;
        this._id_user = _id_user;
        this.total_price = total_price;
        this.address = address;
        Status = status;
    }

    public Cart() {
    }

    public Cart(Integer _id, Integer _id_user, Double total_price, String address) {
        this._id = _id;
        this._id_user = _id_user;
        this.total_price = total_price;
        this.address = address;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer get_id_user() {
        return _id_user;
    }

    public void set_id_user(Integer _id_user) {
        this._id_user = _id_user;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
