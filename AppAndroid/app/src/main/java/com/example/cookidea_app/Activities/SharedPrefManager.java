package com.example.cookidea_app.Activities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cookidea_app.Backend.LoginRequest;
import com.example.cookidea_app.ModelClasses.User;

public class SharedPrefManager {


    protected static final String PREF_NAME = "MyAppPrefs";
    protected static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    protected static final String LOGGED_USER = "loggedUser";
    protected static final String LOGGED_USER_PASSWORD = "loggedUserPassword";



    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void setLoggedIn(Context context, User user, boolean isLoggedIn){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(LOGGED_USER, user.getUsername());
        editor.putString(LOGGED_USER_PASSWORD, user.getPassword());
        editor.commit();
    }

    public static void logout(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.commit();
    }

    public static boolean isLoggedIn(Context context){
        return getSharedPreferences(context).getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public static LoginRequest getLoginRequestFromSharedPref(Context context){
        String username = getSharedPreferences(context).getString(LOGGED_USER, null);
        String password = getSharedPreferences(context).getString(LOGGED_USER_PASSWORD, null);
        return new LoginRequest(username, password);
    }


}
