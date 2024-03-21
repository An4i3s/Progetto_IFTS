package com.example.cookidea_app;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CookIdeaApiEndpointInterface {
    @GET("/users/{username}")
    Call<String> getUser(@Path("username") String username);
}
