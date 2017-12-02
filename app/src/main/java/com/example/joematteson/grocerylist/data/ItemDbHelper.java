package com.example.joematte.grocerylist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joematte on 8/31/17.
 */

public class ItemDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "itemdatabase.db";

    public static final int DATABASE_VERSION = 1;

    private static final String CREATE_DATABASE_SQL_COMMAND = "CREATE TABLE " + ItemContract.ItemEntry.TABLE_NAME
            + "(" + ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY, "
            + ItemContract.ItemEntry.COLUMN_NAME + " TEXT NOT NULL, "
            + ItemContract.ItemEntry.COLUMN_AREA + " TEXT NOT NULL)";

    private static final String DELETE_TABLE_FOR_UPGRADE = "DROP TABLE IF EXISTS " +
            ItemContract.ItemEntry.TABLE_NAME + ";";

    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DATABASE_SQL_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_TABLE_FOR_UPGRADE);
        onCreate(sqLiteDatabase);
    }
}
