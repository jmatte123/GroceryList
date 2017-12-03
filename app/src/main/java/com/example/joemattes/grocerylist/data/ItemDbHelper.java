package com.example.joemattes.grocerylist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joematte on 8/31/17.
 */

/**
 * This class created the database or updates it
 */
public class ItemDbHelper extends SQLiteOpenHelper {

    // --- Fields --- //
    public static final String DATABASE_NAME = "itemdatabase.db";
    public static final int DATABASE_VERSION = 1;

    //create database
    private static final String CREATE_DATABASE_SQL_COMMAND = "CREATE TABLE " + ItemContract.ItemEntry.TABLE_NAME
            + "(" + ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY, "
            + ItemContract.ItemEntry.COLUMN_NAME + " TEXT NOT NULL, "
            + ItemContract.ItemEntry.COLUMN_AREA + " TEXT NOT NULL)";

    //remove and update database
    private static final String DELETE_TABLE_FOR_UPGRADE = "DROP TABLE IF EXISTS " +
            ItemContract.ItemEntry.TABLE_NAME + ";";

    /**
     * this is the constructor for the DBHelper
     * @param context the current state that this is being created on
     */
    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * creates the database using the sql string
     * @param sqLiteDatabase the current database connection
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DATABASE_SQL_COMMAND);
    }

    /**
     * Upgrades the database using the sql delete and update string
     * @param sqLiteDatabase the current database connection
     * @param i ?
     * @param i1 ?
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_TABLE_FOR_UPGRADE);
        onCreate(sqLiteDatabase);
    }
}
