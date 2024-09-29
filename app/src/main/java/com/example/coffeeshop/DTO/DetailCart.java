package com.example.coffeeshop.DTO;

public class DetailCart {
    private Integer _id;
    private Integer _id_cart;
    private Integer _id_product;
    private  double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    private Integer quantity;

    public DetailCart(Integer _id, Integer _id_cart, Integer _id_product, Integer quantity) {
        this._id = _id;
        this._id_cart = _id_cart;
        this._id_product = _id_product;
        this.quantity = quantity;
    }

    public DetailCart() {
    }



    public Integer get_id_cart() {
        return _id_cart;
    }

    public void set_id_cart(Integer _id_cart) {
        this._id_cart = _id_cart;
    }

    public Integer get_id_product() {
        return _id_product;
    }

    public void set_id_product(Integer _id_product) {
        this._id_product = _id_product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
