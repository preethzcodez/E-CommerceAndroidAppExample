package com.preethzcodez.ecommerceexample.database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Preeth on 1/6/2018.
 */

public class SessionManager {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    private SharedPreferences SharedPref;
    private SharedPreferences.Editor SharedPref_Editor;

    public SessionManager(Context context) {
        this.SharedPref = context.getSharedPreferences("com.preethzcodez.ecommerceexample.database.PREFERENCE.", Activity.MODE_PRIVATE);
        this.SharedPref_Editor = SharedPref.edit();
    }

    public void saveSession(String Key, String Value) {
        SharedPref_Editor.putString(Key, Value);
        SharedPref_Editor.commit();
    }

    public String getSessionData(String Key) {
        return SharedPref.getString(Key, "");
    }

    public void clearPreferences() {
        SharedPref_Editor.clear();
        SharedPref_Editor.commit();
    }
}
