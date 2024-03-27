package com.example.cookidea_app.Activities;

import android.content.ContentProvider;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {


    protected static final String PREF_NAME = "MyAppPrefs";
    protected static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void setLoggedIn(Context context, boolean isLoggedIn){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public static boolean isLoggedIn(Context context){
        return getSharedPreferences(context).getBoolean(KEY_IS_LOGGED_IN, false);
    }


}
