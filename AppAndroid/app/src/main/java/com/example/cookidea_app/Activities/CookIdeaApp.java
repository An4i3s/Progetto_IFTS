package com.example.cookidea_app.Activities;

import android.app.Application;
import android.util.Log;

import com.example.cookidea_app.ModelClasses.User;

public class CookIdeaApp extends Application {

    //retrofit da inserire qui la sua inizializzazione ecc

    //+ sharedPred e API

    //provo a togliere == null
     User loggedUser = null;

    public void saveSaredPref(String info) {
        Log.i("CookIdeaApp", info);
    }

    public void setLoggedUser(User u) {
        loggedUser = u;
    }
    public User getLoggedUser() {
        return loggedUser;
    }

    public void logout() {
        loggedUser = null;
    }

}
