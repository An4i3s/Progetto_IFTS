package com.example.cookidea_app.Activities;

import android.app.Application;
import android.util.Log;

import com.example.cookidea_app.Backend.CookIdeaApiEndpointInterface;
import com.example.cookidea_app.ModelClasses.User;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CookIdeaApp extends Application {

    //retrofit da inserire qui la sua inizializzazione ecc

    //+ sharedPred e API

    //provo a togliere == null
     User loggedUser = null;

    public static final String BASE_URL = "http://192.168.0.114:8000";


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static final CookIdeaApiEndpointInterface apiService = retrofit.create(CookIdeaApiEndpointInterface.class);
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
