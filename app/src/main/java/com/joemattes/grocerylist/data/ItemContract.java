package com.joemattes.grocerylist.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by joematte on 8/26/17.
 */

/**
 * this class is the contract for the database.
 * Which means it sets up the table and columns for the database.
 */
public class ItemContract {

    private ItemContract(){}
    /**
     * The inner class defines the database table name and column names.
     */
    public static final class ItemEntry implements BaseColumns{

        /**
         * Table name.
         */
        public static final String TABLE_NAME = "list";

        /**
         * Any columns that I will need for the table.
         * The id column is already implemented with the BaseColumns interface.
         */
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AREA = "area";

    }
}
