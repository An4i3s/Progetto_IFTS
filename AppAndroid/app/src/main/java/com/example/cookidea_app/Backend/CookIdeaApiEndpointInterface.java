package com.example.cookidea_app.Backend;

import android.icu.text.UnicodeSet;

import com.example.cookidea_app.ModelClasses.Meal;
import com.example.cookidea_app.ModelClasses.Recipe;
import com.example.cookidea_app.ModelClasses.Serving;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.ModelClasses.WeeklyMenu;


import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CookIdeaApiEndpointInterface {
    @GET("/api/portate")
    Call<List<Serving>> getPortate();

    @GET("/api/randomPiattoIdNomeImg")
    Call<List<Recipe>> getRandomRecipe();

    @GET("/api/ricercaPerNome/{nome}")
    Call<List<Recipe>> getRecipeByName(@Path("nome") String recipeName, @Query("id_utente") long userId);

    @GET("/api/ricercaPerPortata/{portata}")
    Call<List<Recipe>> getRecipeByServing(@Path("portata") String serving);

    @GET("/api/ricerca/ricettaFromId")
    Call<Recipe> getRecipeById(@Query("id_piatto") String recipeId);

    @GET("/api/menuSettimanale")
    Call<List<WeeklyMenu>> getWeeklyMenu(@Query("id_utente") int userId);

    @POST("api/login")
    Call<User> login(@Body LoginRequest request);

    @POST("api/signup")
    Call<User> signup(@Body User request);

    @GET("/api/checkPreferito")
    Call<Integer> checkPreferito(@Query("id_utente") long idUtente, @Query("id_piatto") String idPiatto);

    @GET("/api/updatePreferito")
    Call<Integer> updatePreferito(@Query("id_utente") long idUtente, @Query("id_piatto") String idPiatto);

    @GET("api/preferitiFromIdUtente")
    Call<List<Recipe>> getPreferitiFromId(@Query("id_utente") long idUtente);

    @PUT("api/agg_DatiUtente")
    Call<User> updateDatiUtente(@Body User updateUser);
    // Call<User> updateDatiUtente(@Body UpdateRequest request);

    @GET("/api/getTipoPasto")
    Call<List<Meal>> getMeals();

    @PUT("/api/insertWeeklyMenu")
    Call<Integer> addWeeklyMenu(@Query("id_utente") long idUtente, @Query("id_piatto") String idPiatto, @Query("id_pasto") int idPasto, @Query("data") String data);



}
