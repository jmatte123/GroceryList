package com.example.joematte.grocerylist.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

import com.example.joematte.grocerylist.R;

/**
 * Created by joematte on 9/2/17.
 */

public class LayoutUtils {
    public static int getColorFromItemArea(Context context, String area){
        switch (area) {
            case "Produce": return ContextCompat.getColor(context, R.color.red);
            case "Grocery": return ContextCompat.getColor(context, R.color.green);
            case "Extra": return ContextCompat.getColor(context, R.color.grey);
            case "Meat": return ContextCompat.getColor(context, R.color.darkRed);
            case "Frozen": return ContextCompat.getColor(context, R.color.blue);
            case "Dairy": return ContextCompat.getColor(context, R.color.cream);
            default: return 0;
        }
    }
}
