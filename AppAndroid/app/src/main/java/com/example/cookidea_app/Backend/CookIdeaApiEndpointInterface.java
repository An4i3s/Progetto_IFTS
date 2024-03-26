package com.example.cookidea_app.Backend;

import com.example.cookidea_app.ModelClasses.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CookIdeaApiEndpointInterface {
    @GET("/api/portate")
    Call<List<String>> getPortate();

    @GET("/api/ricercaPerNome/{nome}")
    Call<List<Recipe>> getRecipeByName(@Path("nome") String nome);

    @GET("/api/ricercaPerPortata/{portata}")
    Call<List<Recipe>> getRecipeByServing(@Path("portata") String portata);
}
