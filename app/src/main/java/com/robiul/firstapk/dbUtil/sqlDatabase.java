package com.robiul.firstapk.dbUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class sqlDatabase extends SQLiteOpenHelper {

   final static String NAME = "INVENTORY";

   public static final String TBL_NAME = "PRODUCT";
   public static final String COL_NAME = "NAME";
   public static final String COL_EMAIL = "EMAIL";
   public static final String COL_PRICE = "PRICE";
   public static final String COL_QUANTITY = "QUANTITY";




    public sqlDatabase(@Nullable Context context) {
        super(context, NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TBL_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, " + COL_EMAIL + " TEXT, " + COL_PRICE + " REAL, " + COL_QUANTITY + " INTEGER)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
