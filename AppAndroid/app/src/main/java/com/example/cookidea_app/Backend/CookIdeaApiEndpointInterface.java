package com.example.cookidea_app.Backend;

import android.icu.text.UnicodeSet;

import com.example.cookidea_app.ModelClasses.Recipe;
import com.example.cookidea_app.ModelClasses.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CookIdeaApiEndpointInterface {
    @GET("/api/portate")
    Call<List<String>> getPortate();

    @GET("/api/ricercaPerNome/{nome}")
    Call<List<Recipe>> getRecipeByName(@Path("nome") String nome);

    //@POST("api/login2")
   // Call<Response> login(@Path("username") String username, @Path("password") String password);

    // TODO: 26/03/2024 FARE CLASSE LOGIN
  //  @POST("api/login/{username}&&{password}")
   // Call<User> login(@Query("username") String username, @Query("password") String password);

    @POST("api/login")
    Call<User> login(@Body LoginRequest request);
}
