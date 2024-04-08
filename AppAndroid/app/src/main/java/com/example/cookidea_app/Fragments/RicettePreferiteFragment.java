package com.example.cookidea_app.Fragments;

import static com.example.cookidea_app.Activities.CookIdeaApp.apiService;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookidea_app.Activities.CookIdeaApp;
import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Adapters.SearchPageListAdapter;
import com.example.cookidea_app.Backend.CookIdeaApiEndpointInterface;
import com.example.cookidea_app.ModelClasses.Recipe;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RicettePreferiteFragment extends Fragment {

    Context ctx;
    ListView preferitiLV = null;

    List<Recipe> listaRicettePreferite;
    SearchPageListAdapter searchPageListAdapter;
    //Retrofit retrofit;
    //CookIdeaApiEndpointInterface apiInterface;
    //= new Retrofit.Builder().baseUrl(MainActivity.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    User user;


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

        listaRicettePreferite = new ArrayList<>();

        preferitiLV = rootview.findViewById(R.id.ricettePrefeListView);

        // TODO: 04/04/2024 togliere retorift e apiInterface e usare apiService 
        //Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        //apiInterface = retrofit.create(CookIdeaApiEndpointInterface.class);

        user = ((CookIdeaApp)((MainActivity)ctx).getApplication()).getLoggedUser();
        Call<List<Recipe>> call = apiService.getPreferitiFromId(user.getId());

        searchPageListAdapter = new SearchPageListAdapter(ctx, listaRicettePreferite);
        preferitiLV.setAdapter(searchPageListAdapter);
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                listaRicettePreferite = response.body();
                //assert listaRicettePreferite != null;
                if (listaRicettePreferite != null){

                    searchPageListAdapter.addAll(listaRicettePreferite);
                    searchPageListAdapter.notifyDataSetChanged();
                    preferitiLV.invalidate();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("Ricette Preferite", t.getMessage());
            }
        });

        preferitiLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ctx, searchPageListAdapter.getItem(position).getName(), Toast.LENGTH_LONG).show();
                ((MainActivity)ctx).changeTabById(R.id.searchPage, searchPageListAdapter.getItem(position).getRecipeId());
                ((MainActivity)ctx).changeFrameByNavigationTab(R.id.recipePage);
            }
        });
        return rootview;
    }
}
