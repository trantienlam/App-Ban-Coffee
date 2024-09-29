package com.example.coffeeshop.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.widget.Toast;

import com.example.coffeeshop.DTO.Cart;
import com.example.coffeeshop.DTO.Category;
import com.example.coffeeshop.DTO.DetailCart;
import com.example.coffeeshop.DTO.Product;
import com.example.coffeeshop.DTO.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseHandler  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "coffeesManager";
    private static final String TABLE_PRODUCT = "product";
    private static final String TABLE_USER = "user";
    private static final String TABLE_CART = "cart";
    private static final String TABLE_DETAIL_CART = "detail_cart";

    private static final String TABLE_CATEGORY = "category";
    private static final String KEY_ID = "id";
    private static final String KEY_ID_CATE = "id_cate";

    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_DES = "description";
    private static final String KEY_IMG = "image";
    private static final AtomicInteger index = new AtomicInteger(1);
    private static final String KEY_FAVOURITE = "favourite";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONE_NUMBER = "phone_number";

    private static final String KEY_TOTAL_PRICE = "total_price";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_ID_USER = "id_user";
    private static final String KEY_ID_CART = "id_cart";
    private static final String KEY_STATUS_CART = "status";

    private static final String KEY_ID_PRODUCT = "id_product";
    private static final String KEY_QUANTITY = "quantity";
    private Context context;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //  Bỏ bảng cũ hơn nếu đã tồn tại
        String CreateTableProduct = "create table " + TABLE_PRODUCT + "(\n" +
                "\t" + KEY_ID + " integer primary key AUTOINCREMENT,\n" +
                "\t" + KEY_ID_CATE + " integer,\n" +
                "\t" + KEY_NAME + " text,\n" +
                "\t" + KEY_PRICE + " double,\n" +
                "\t" + KEY_DES + " text,\n" +
                "\t" + KEY_IMG + " BLOB,\n" +
                "\t" + KEY_FAVOURITE + " bool\n" +
                ")";
        String CreateTableCategory = "create table " + TABLE_CATEGORY + "(\n" +
                "\t" + KEY_ID + " integer primary key AUTOINCREMENT,\n" +
                "\t" + KEY_NAME + " text)";
        String CreateTableUser = "create table " + TABLE_USER + "(\n" +
                "\t" + KEY_ID + " integer primary key AUTOINCREMENT,\n" +
                "\t" + KEY_USERNAME + " text,\n" +
                "\t" + KEY_PASSWORD + " text,\n" +
                "\t" + KEY_NAME + " text,\n" +
                "\t" + KEY_PHONE_NUMBER + " text\n" +
                ")";
        String CreateTableCart = "create table " + TABLE_CART + "(\n" +
                "\t" + KEY_ID + " integer primary key AUTOINCREMENT,\n" +
                "\t" + KEY_TOTAL_PRICE + " double,\n" +
                "\t" + KEY_ADDRESS + " text,\n" +
                "\t" + KEY_ID_USER + " integer,\n" +
                "\t" + KEY_STATUS_CART + " integer\n" +
                ")";
        String CreateTableDetailCart = "create table " + TABLE_DETAIL_CART + "(\n" +
                "\t" + KEY_ID + " integer primary key AUTOINCREMENT,\n" +
                "\t" + KEY_ID_CART + " integer,\n" +
                "\t" + KEY_ID_PRODUCT + " integer,\n" +
                "\t" + KEY_QUANTITY + " integer,\n" +
                "\t" + KEY_PRICE + " double\n" +
                ")";
        sqLiteDatabase.execSQL(CreateTableCategory);
        sqLiteDatabase.execSQL(CreateTableProduct);
        sqLiteDatabase.execSQL(CreateTableUser);
        sqLiteDatabase.execSQL(CreateTableCart);
        sqLiteDatabase.execSQL(CreateTableDetailCart);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public boolean updateLocationAndPhone(int accountId, String newName, String newPhone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, newName);
        contentValues.put(KEY_PHONE_NUMBER, newPhone);
//Xác định điều kiện WHERE để tìm bản ghi cần cập nhật
        String whereClause = KEY_ID + " = ?";
        String[] whereArgs = { String.valueOf(accountId) };

        int rowsAffected = db.update(TABLE_USER, contentValues, whereClause, whereArgs);//Cập nhật cơ sở dữ liệu và lấy số lượng hàng bị ảnh hưởng
        db.close();
        return rowsAffected > 0;//Trả về true nếu có ít nhất một hàng bị ảnh hưởng, ngược lại trả về false
    }
    public Cursor getAccountData(int accountId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + KEY_NAME + ", " + KEY_PHONE_NUMBER +
                " FROM " + TABLE_USER +
                " WHERE " + KEY_ID + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(accountId)});
    }
    public boolean updatePassword(String USERNAME, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, newPassword);

        String selection = KEY_USERNAME + " = ?";
        String[] selectionArgs = { USERNAME };

        int count = db.update(
                TABLE_USER,
                values,
                selection,
                selectionArgs);

        db.close();
        return count > 0;
    }
    // them san pham
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();// nhận giá trị
        //values.put(KEY_ID, index.getAndIncrement());
        values.put(KEY_ID_CATE, product.get_id_cate());
        values.put(KEY_NAME, product.getName());
        values.put(KEY_PRICE, product.getPrice());
        values.put(KEY_DES, product.getDescription());
        values.put(KEY_IMG, product.getImageBook());
        values.put(KEY_FAVOURITE, product.isFavourite());
        // Inserting Row
        db.insert(TABLE_PRODUCT, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public void addCategory(Category cate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, cate.getNameCategory());
        // Inserting Row
        db.insert(TABLE_CATEGORY, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public int getCoffeeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.set_id(Integer.parseInt(cursor.getString(0)));
                product.set_id_cate(Integer.parseInt(cursor.getString(1)));
                product.setName(cursor.getString(2));
                product.setPrice(Double.parseDouble(cursor.getString(3)));
                product.setDescription(cursor.getString(4));
                byte[] imageBytes = cursor.getBlob(5);
                product.setImageBook(imageBytes);
                String temp = cursor.getString(6);
                if (temp.equals("1")) {
                    product.setFavourite(true);
                } else {
                    product.setFavourite(false);
                }

                // Adding product to list
                productList.add(product);
            } while (cursor.moveToNext());
        }

        // return product list
        return productList;
    }

    public List<Product> getAlProductsIsFavourite() {
        List<Product> productList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT + " where favourite = 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.set_id(Integer.parseInt(cursor.getString(0)));
                product.set_id_cate(Integer.parseInt(cursor.getString(1)));
                product.setName(cursor.getString(2));
                product.setPrice(Double.parseDouble(cursor.getString(3)));
                product.setDescription(cursor.getString(4));
                byte[] imageBytes = cursor.getBlob(5);
                product.setImageBook(imageBytes);
                String temp = cursor.getString(6);
                if (temp.equals("1")) {
                    product.setFavourite(true);
                } else {
                    product.setFavourite(false);
                }
                // Adding product to list
                productList.add(product);
            } while (cursor.moveToNext());
        }

        // return product list
        return productList;
    }

    public Product getProductByID(int id) {
        String selectQuery = "select * from " + TABLE_PRODUCT + " where id =" + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Product product = new Product();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                product.set_id(Integer.parseInt(cursor.getString(0)));
                product.set_id_cate(Integer.parseInt(cursor.getString(1)));
                product.setName(cursor.getString(2));
                product.setPrice(Double.parseDouble(cursor.getString(3)));
                product.setDescription(cursor.getString(4));
                byte[] imageBytes = cursor.getBlob(5);
                product.setImageBook(imageBytes);
                String temp = cursor.getString(6);
                if (temp.equals("1")) {
                    product.setFavourite(true);
                } else {
                    product.setFavourite(false);
                }
                // Adding product to list
            } while (cursor.moveToNext());
        }
        return product;
    }

    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_PRICE, product.getPrice());
        values.put(KEY_DES, product.getDescription());
        values.put(KEY_IMG, product.getImageBook());
        values.put(KEY_FAVOURITE, product.isFavourite());
        values.put(KEY_ID_CATE, product.get_id_cate());

        // updating row

       db.update(TABLE_PRODUCT, values, KEY_ID + " = ?",
                new String[]{String.valueOf(product.get_id())});

        db.close();
        return 1;
    }
    //lay du lieu
    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category cate = new Category();
                cate.set_id(Integer.parseInt(cursor.getString(0)));
                cate.setNameCategory(cursor.getString(1));
                // Adding product to list
                categoryList.add(cate);
            } while (cursor.moveToNext());
        }

        // return product list
        return categoryList;
    }

    public Category getCategoryByID(int id) {
        String selectQuery = "select * from " + TABLE_CATEGORY + " where id =" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Category cate = new Category();
        if (cursor.moveToFirst()) {
            do {
                cate.set_id(Integer.parseInt(cursor.getString(0)));
                cate.setNameCategory(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return cate;
    }

    public Cart getCartByUserID(int id) {

        String selectQuery = "select * from " + TABLE_CART + " where id_user = " + id + " and status = 0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Cart match = new Cart();

        // lặp qua tất cả các hàng và thêm vào danh sách
        if (cursor.moveToFirst()) {
            do {
                match.set_id(Integer.parseInt(cursor.getString(0)));
                match.setTotal_price(Double.parseDouble(cursor.getString(1)));
                match.setAddress(cursor.getString(2));
                match.set_id_user(Integer.parseInt(cursor.getString(3)));
                match.setStatus(Integer.parseInt(cursor.getString(4)));
                // Adding product to list
            } while (cursor.moveToNext());
        }
        return match;
    }

    public List<DetailCart> getAllDetailCartByUser_CartID(int userID, int cartID) {
        List<DetailCart> detailCartList = new ArrayList<DetailCart>();
        // Chọn tất cả truy vấn
        String selectQuery = "SELECT  * FROM " + TABLE_DETAIL_CART + " where id_cart = " + cartID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DetailCart detail = new DetailCart();
                detail.set_id(Integer.parseInt(cursor.getString(0)));
                detail.set_id_cart(Integer.parseInt(cursor.getString(1)));
                detail.set_id_product(Integer.parseInt(cursor.getString(2)));
                detail.setQuantity(Integer.parseInt(cursor.getString(3)));
                detail.setPrice(Double.parseDouble(cursor.getString(4)));
                // Adding detail cart to list
                detailCartList.add(detail);
            } while (cursor.moveToNext());
        }

        // return product list
        return detailCartList;
    }

    public int updateDetail(DetailCart cart) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CART, cart.get_id_cart());
        values.put(KEY_ID_PRODUCT, cart.get_id_product());
        values.put(KEY_QUANTITY, cart.getQuantity());
        values.put(KEY_PRICE, cart.getPrice());

        // updating row
        return db.update(TABLE_DETAIL_CART, values, KEY_ID_CART + " =  ? AND " + KEY_ID_PRODUCT + " = ?",
                new String[]{String.valueOf(cart.get_id_cart()), String.valueOf(cart.get_id_product())});
    }

    public void deleteDetail(DetailCart cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DETAIL_CART, KEY_ID_CART + " =  ? AND " + KEY_ID_PRODUCT + " = ?", new String[]{String.valueOf(cart.get_id_cart()), String.valueOf(cart.get_id_product())});
        db.close();
    }

    public int getUserIdLogged(String Username, String Password) {
        String countQuery = "SELECT  * FROM " + TABLE_USER + " where  username = '" + Username + "' and password = '" + Password + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.getCount() == 0)
            return -1;
        int id = 0;
        if (cursor.moveToFirst()) {
            id = Integer.parseInt(cursor.getString(0));
        }
        return id;
    }

    public void AddToCart(int id_Product, int id_User) {
        Cart match = this.getCartByUserID(id_User);
        Product product = this.getProductByID(id_Product);
        SQLiteDatabase db = this.getWritableDatabase();
        if (match.get_id() == null) { // thực hiện tạo giỏ hàng
            ContentValues values = new ContentValues();
            values.put(KEY_TOTAL_PRICE, 0);
            values.put(KEY_ADDRESS, "Ho Chi Minh CITY");
            values.put(KEY_ID_USER, id_User);
            values.put(KEY_STATUS_CART, 0);
            // Inserting Row
            db.insert(TABLE_CART, null, values);
            // thêm sản phẩm vào chi tiết giỏ hàng
            Cart newInsert = getCartByUserID(id_User);
            ContentValues values_detailCart = new ContentValues();
            values_detailCart.put(KEY_ID_CART, newInsert.get_id());
            values_detailCart.put(KEY_ID_PRODUCT, id_Product);
            values_detailCart.put(KEY_QUANTITY, 1);
            values_detailCart.put(KEY_PRICE, product.getPrice());
            db.insert(TABLE_DETAIL_CART, null, values_detailCart);
            Toast.makeText(context, "Add to cart successfully", Toast.LENGTH_SHORT).show();
            db.close(); // Closing database connection
        } else // nếu đã tồn tại
        {
            //TH1: món hàng này đã có sẵn trong giỏ hàng
            String countQuery = "SELECT  * FROM " + TABLE_DETAIL_CART + " where  id_cart = " + match.get_id() + " and id_product = " + id_Product;
            Cursor cursor = db.rawQuery(countQuery, null);
            if (cursor.getCount() != 0) {
                Toast.makeText(context, "Already in cart, please check.", Toast.LENGTH_SHORT).show();
                return;
            }
            //TH2: chưa tồn tại thì thêm vào chi tiết giỏ hàng
            ContentValues values_detailCart = new ContentValues();
            values_detailCart.put(KEY_ID_CART, match.get_id());
            values_detailCart.put(KEY_ID_PRODUCT, id_Product);
            values_detailCart.put(KEY_QUANTITY, 1);
            values_detailCart.put(KEY_PRICE, product.getPrice());
            db.insert(TABLE_DETAIL_CART, null, values_detailCart);
            Toast.makeText(context, "Add to cart successfully", Toast.LENGTH_SHORT).show();
            db.close(); // Closing database connection
        }

    }

    public double getTotalByUser_Cart(int userID, int cartID) {
        Double total = 0d;

        List<DetailCart> detailCartList = getAllDetailCartByUser_CartID(userID, cartID);
        for (DetailCart i : detailCartList) {
            total += i.getQuantity() * i.getPrice();
        }
        User user = this.getUserById(userID);
        // update total price of user
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TOTAL_PRICE, total);
        values.put(KEY_ID_USER, userID);

        // updating row
        db.update(TABLE_CART, values, KEY_ID + " = ?",
                new String[]{String.valueOf(Integer.toString(cartID))});
        db.close();
        return total;
    }

    public void addDefaultUser() {

        SQLiteDatabase db = this.getWritableDatabase();

        String countQuery = "SELECT  * FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(KEY_USERNAME, "admin");
            values.put(KEY_PASSWORD, "admin");
            values.put(KEY_NAME, "admin");
            values.put(KEY_PHONE_NUMBER, "123");
            db.insert(TABLE_USER, null, values);
        }
        countQuery = "SELECT  * FROM " + TABLE_CATEGORY;
        cursor = db.rawQuery(countQuery,null);
        if (cursor.getCount() == 0) {
            this.addCategory(new Category("Coffee",1));
            this.addCategory(new Category("Trà",1));
            this.addCategory(new Category("Bánh",1));
        }

        db.close();


    }

    public void Register(User newUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, newUser.get_username());
        values.put(KEY_PASSWORD, newUser.get_password());
        values.put(KEY_NAME, newUser.getFull_name());
        values.put(KEY_PHONE_NUMBER, newUser.getPhone_number());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

public User getUserById(int userIdLogged) {
            User user = new User();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " where id = " + userIdLogged;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            user.set_id(Integer.parseInt(cursor.getString(0)));
            user.set_username(cursor.getString(1));
            user.set_password(cursor.getString(2));
            user.setFull_name(cursor.getString(3));
            user.setPhone_number(cursor.getString(4));
        }
        return user;
    }

    public void PlaceOrder(int userIdLogged, String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STATUS_CART, 1);
        values.put(KEY_ADDRESS, address);

        // updating row
        db.update(TABLE_CART, values, KEY_ID_USER + " =  ? AND " + KEY_STATUS_CART + " = ?", new String[]{String.valueOf(userIdLogged), String.valueOf(0)
        });
        db.close();
    }
    public List<Cart> getAllCarts() {
        List<Cart> cartList = new ArrayList<Cart>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.set_id(Integer.parseInt(cursor.getString(0)));
                cart.setTotal_price(Double.parseDouble(cursor.getString(1)));
                cart.setAddress(cursor.getString(2));
                cart.set_id_user(Integer.parseInt(cursor.getString(3)));
                cart.setStatus(Integer.parseInt(cursor.getString(4)));
                // Adding cart to list
                cartList.add(cart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // Return cart list
        return cartList;
    }


    public List<Cart> getAllCartsByUser_Ordered(int userIdLogged) {
        List<Cart> cartList = new ArrayList<Cart>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CART + " where id_user = " + userIdLogged +" and status = 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.set_id(Integer.parseInt(cursor.getString(0)));
                cart.setTotal_price(Double.parseDouble(cursor.getString(1)));
                cart.setAddress(cursor.getString(2));
                cart.set_id_user(Integer.parseInt(cursor.getString(3)));
                cart.setStatus(Integer.parseInt(cursor.getString(4)));
                // Adding detail cart to list
                cartList.add(cart);
            } while (cursor.moveToNext());
        }

        // return product list
        return cartList;

    }

    public List<DetailCart> getAllDetailCartByCartID(int idCart) {
        List<DetailCart> detailCartList = new ArrayList<DetailCart>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DETAIL_CART + " where id_cart = " + idCart;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DetailCart detail = new DetailCart();
                detail.set_id(Integer.parseInt(cursor.getString(0)));
                detail.set_id_cart(Integer.parseInt(cursor.getString(1)));
                detail.set_id_product(Integer.parseInt(cursor.getString(2)));
                detail.setQuantity(Integer.parseInt(cursor.getString(3)));
                detail.setPrice(Double.parseDouble(cursor.getString(4)));
                // Adding detail cart to list
                detailCartList.add(detail);
            } while (cursor.moveToNext());
        }

        // return product list
        return detailCartList;

    }

    public Cart getCartByID(Integer id_cart) {

        String selectQuery = "select * from " + TABLE_CART + " where id = " + id_cart;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Cart match = new Cart();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                match.set_id(Integer.parseInt(cursor.getString(0)));
                match.setTotal_price(Double.parseDouble(cursor.getString(1)));
                match.setAddress(cursor.getString(2));
                match.set_id_user(Integer.parseInt(cursor.getString(3)));
                match.setStatus(Integer.parseInt(cursor.getString(4)));
                // Adding product to list
            } while (cursor.moveToNext());
        }
        return match;
    }
}