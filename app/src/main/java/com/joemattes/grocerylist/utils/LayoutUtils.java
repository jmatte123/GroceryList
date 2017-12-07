package com.joemattes.grocerylist.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

import com.joemattes.grocerylist.R;

/**
 * Created by joematte on 9/2/17.
 */

/**
 * This class is for static utilities
 */
public class LayoutUtils {

    /**
     * get the color area from the current context and the color string
     * @param context the current state of the method that is calling this
     * @param area the string area
     * @return the color depending on the area of the given area
     */
    public static int getColorFromItemArea(Context context, String area){
        switch (area) {
            case "Produce": return ContextCompat.getColor(context, R.color.red);
            case "Grocery": return ContextCompat.getColor(context, R.color.green);
            case "Other": return ContextCompat.getColor(context, R.color.grey);
            case "Meat": return ContextCompat.getColor(context, R.color.darkRed);
            case "Frozen": return ContextCompat.getColor(context, R.color.blue);
            case "Dairy": return ContextCompat.getColor(context, R.color.cream);
            default: return 0;
        }
    }
}
