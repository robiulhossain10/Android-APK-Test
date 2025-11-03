package com.robiul.firstapk.dbUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.robiul.firstapk.products.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private sqlDatabase dbHelper;

    public ProductDao(Context context) {
        dbHelper = new sqlDatabase(context);
    }

    // 🔹 INSERT
    public long insert(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(sqlDatabase.COL_NAME, product.getName());
        values.put(sqlDatabase.COL_EMAIL, product.getEmail());
        values.put(sqlDatabase.COL_PRICE, product.getPrice());
        values.put(sqlDatabase.COL_QUANTITY, product.getQuantity());

        long id = db.insert(sqlDatabase.TBL_NAME, null, values);
        db.close();
        return id;
    }

    // 🔹 READ ALL
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + sqlDatabase.TBL_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Product p = new Product();
                p.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                p.setName(cursor.getString(cursor.getColumnIndexOrThrow(sqlDatabase.COL_NAME)));
                p.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(sqlDatabase.COL_EMAIL)));
                p.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(sqlDatabase.COL_PRICE)));
                p.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(sqlDatabase.COL_QUANTITY)));

                list.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // 🔹 UPDATE
    public int update(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(sqlDatabase.COL_NAME, product.getName());
        values.put(sqlDatabase.COL_EMAIL, product.getEmail());
        values.put(sqlDatabase.COL_PRICE, product.getPrice());
        values.put(sqlDatabase.COL_QUANTITY, product.getQuantity());

        int rows = db.update(sqlDatabase.TBL_NAME, values, "ID = ?", new String[]{String.valueOf(product.getId())});
        db.close();
        return rows;
    }

    // 🔹 DELETE
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(sqlDatabase.TBL_NAME, "ID = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }
}
