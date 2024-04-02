package com.example.cookidea_app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookidea_app.Activities.CookIdeaApp;
import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Activities.SharedPrefManager;
import com.example.cookidea_app.Adapters.SearchPageListAdapter;
import com.example.cookidea_app.Backend.CookIdeaApiEndpointInterface;
import com.example.cookidea_app.ModelClasses.Recipe;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RicettePreferiteFragment extends Fragment {

    Context ctx = null;
    //User user;
    List<Recipe> favRecipes;

    ListView preferitiLv;
    SearchPageListAdapter searchPageListAdapter;
    Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    CookIdeaApiEndpointInterface apiInterface;

    public RicettePreferiteFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_ricette_preferite, container, false);

        favRecipes = new ArrayList<>();
        apiInterface = retrofit.create(CookIdeaApiEndpointInterface.class);
        MainActivity.user = ((CookIdeaApp)((MainActivity)ctx).getApplication()).getLoggedUser();
        preferitiLv = rootview.findViewById(R.id.listaRicettePreferite);
        searchPageListAdapter = new SearchPageListAdapter(ctx, favRecipes);
        preferitiLv.setAdapter(searchPageListAdapter);

        if (MainActivity.user==null){
            SharedPrefManager.setLoggedIn(ctx, true);

        }

        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        downloadBackEndInfo();
    }

    private void downloadBackEndInfo(){
        Call<List<Recipe>> call = apiInterface.getPreferiti(String.valueOf(MainActivity.user.getId()));
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                //List<Recipe> result = response.body();
                favRecipes = response.body();
                if(favRecipes != null) {
                    searchPageListAdapter.addAll(favRecipes);
                    searchPageListAdapter.notifyDataSetChanged();
                    preferitiLv.invalidate();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }
}
