package com.example.coffeeshop.DTO;

public class Category {
    int _id;
    String nameCategory;

    public Category(String nameCategory, int id) {
        this.nameCategory = nameCategory;
        this._id  = id;
    }

    public Category() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
    public String getNameCategory() {
        return nameCategory;
    }
    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public String toString() {
        return nameCategory;
    }
}
