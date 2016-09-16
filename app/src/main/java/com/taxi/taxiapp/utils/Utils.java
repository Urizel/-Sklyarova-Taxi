package com.taxi.taxiapp.utils;

import android.text.TextUtils;

/**
 * Created by kristina on 06.09.16.
 */
public class Utils {

    public static String convertAmount(int amount){
//        String regexp = "[0-9]+.[0-9]{2}";
//        amount = amount.replace(regexp, amount);
//        return amount;

        StringBuilder str = new StringBuilder(String.valueOf(amount));
        str.insert(str.length() - 2, ".");
        return str.toString();
    }
}
