package com.preethzcodez.ecommerceexample.utils;

import android.content.Context;
import android.text.TextUtils;

import com.preethzcodez.ecommerceexample.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Preeth on 1/5/18
 */

// Utilities Class
public class Util {

    // Format Double Value To Remove Unnecessary Zero
    public static String formatDouble(double num) {
        if (num == (long) num)
            return String.format(Locale.US, "%d", (long) num);
        else
            return String.format(Locale.US, "%s", num);
    }

    // Get inClause String For Array Parameters In DB
    public static String getInClause(List<String> array) {
        String inClause = array.toString();

        //replace the brackets with parentheses
        inClause = inClause.replace("[", "(");
        inClause = inClause.replace("]", ")");

        return inClause;
    }

    // Check email valid or not
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Get Error Message
    public static String getErrorMessage(Throwable t, Context context) {
        if (t instanceof SocketTimeoutException || t instanceof UnknownHostException || t instanceof ConnectException) {
            return context.getResources().getString(R.string.NoInternet);
        } else {
            return context.getResources().getString(R.string.Error500);
        }
    }
}
