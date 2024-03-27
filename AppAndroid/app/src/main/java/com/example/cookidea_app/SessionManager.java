package com.example.cookidea_app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context context;
    int PRIVATE_MODE = 0;

    //ALTRO? campi poi dovranno essere modificabili in profilo Fragment

    private static final String PREF_NAME = "AndroidHivePref";

    public static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_NAME = "nome";
    public static final String KEY_SURNAME = "cognome";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_BDAY = "birthday";

    public SessionManager(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public SharedPreferences getPref() {
        return pref;
    }

    /*

     * Create login session

     * */

    public void createLoginSession(String name, String cognome, String email, String username, String password, String bday){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_SURNAME, cognome);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USERNAME,  username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_BDAY, bday);
        editor.commit();

    }

    /**

     * Get stored session data

     * */
    public  HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        return user;

    }



}
