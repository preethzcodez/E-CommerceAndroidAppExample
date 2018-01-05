package com.preethzcodez.ecommerceexample.utils;

import java.util.Locale;

/**
 * Created by Preeth on 1/5/18
 */

// Utilities Class
public class Util {

    // Format Double Value To Remove Unnecessary Zero
    public static String formatDouble(double num)
    {
        if(num == (long) num)
            return String.format(Locale.US,"%d",(long)num);
        else
            return String.format(Locale.US,"%s",num);
    }
}
