package com.example.coffeeshop.DTO;

public class Product{
    int _id;
    int _id_cate;

    public int get_id_cate() {
        return _id_cate;
    }

    public void set_id_cate(int _id_cate) {
        this._id_cate = _id_cate;
    }

    String Name;
    Double Price;
    private byte[] imageBook;
    String Description;

    public boolean isFavourite() {
        return Favourite;
    }

    public void setFavourite(boolean favourite) {
        Favourite = favourite;
    }

    boolean Favourite;
    public String getDescription() {
        return Description;
    }

    public  Product(String name, double price, String des, int id,boolean fav, int id_cate)
    {
        Name = name;
        Price = price;
        Description = des;
        this._id  = id;
        this.Favourite = fav;
        this._id_cate = id_cate;
    }

    public Product(int _id, int _id_cate, String name, Double price, byte[] imageBook, String description, boolean favourite) {
        this._id = _id;
        this._id_cate = _id_cate;
        Name = name;
        Price = price;
        this.imageBook = imageBook;
        Description = description;
        Favourite = favourite;
    }

    public byte[] getImageBook() {
        return imageBook;
    }

    public void setImageBook(byte[] imageBook) {
        this.imageBook = imageBook;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPrice(Double price) {
        Price = price;
    }


    public void setDescription(String description) {
        Description = description;
    }

    public Product() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return Name;
    }



    public Double getPrice() {
        return Price;
    }
}
