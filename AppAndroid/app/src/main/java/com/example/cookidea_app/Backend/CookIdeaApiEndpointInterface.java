package com.example.cookidea_app.Backend;

import android.icu.text.UnicodeSet;

import com.example.cookidea_app.ModelClasses.Recipe;
import com.example.cookidea_app.ModelClasses.Serving;
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
    Call<List<Serving>> getPortate();

    @GET("/api/randomPiattoIdNomeImg")
    Call<List<Recipe>> getRandomRecipe();

    @GET("/api/preferiti/id_utente")
    Call<List<Recipe>> getPreferiti(@Query("id_utente") String id_utente);

    @GET("/api/ricercaPerNome/{nome}")
    Call<List<Recipe>> getRecipeByName(@Path("nome") String recipeName);

    @GET("/api/ricercaPerPortata/{portata}")
    Call<List<Recipe>> getRecipeByServing(@Path("portata") String serving);

    @GET("/api/ricerca/ricettaFromId")
    Call<Recipe> getRecipeById(@Query("id_piatto") String recipeId);

    @POST("api/login")
    Call<User> login(@Body LoginRequest request);


}
