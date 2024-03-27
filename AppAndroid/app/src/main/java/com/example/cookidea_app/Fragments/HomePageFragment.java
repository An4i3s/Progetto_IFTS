package com.example.cookidea_app.Fragments;


import static com.example.cookidea_app.Activities.MainActivity.apiService;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Adapters.CarouselPagerAdapter;
import com.example.cookidea_app.Adapters.HomePageListAdapter;
import com.example.cookidea_app.ModelClasses.Recipe;
import com.example.cookidea_app.ModelClasses.Serving;
import com.example.cookidea_app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomePageFragment extends Fragment {

    Context ctx = null;
    CarouselPagerAdapter carouselPagerAdapter;
    ViewPager carouselViewPager;
    ListView listView;
    List<Serving> listPortate = new ArrayList<>();
    List<Recipe> carouselResult = new ArrayList<>();

    HomePageListAdapter homePageListAdapter;

    public HomePageFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        listView = rootView.findViewById(R.id.categoryListHomeFragment);
        homePageListAdapter = new HomePageListAdapter(ctx, listPortate);
        listView.setAdapter(homePageListAdapter);

        downloadBackEndInfo();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ctx, homePageListAdapter.getItem(position).getServing(), Toast.LENGTH_LONG).show();
                ((MainActivity)ctx).changeTabById(R.id.searchPage, homePageListAdapter.getItem(position).getServing());
            }
        });

        carouselPagerAdapter = new CarouselPagerAdapter(rootView.getContext(), carouselResult);
        carouselViewPager = rootView.findViewById(R.id.carouselViewPagerHomeFragment);
        carouselViewPager.setAdapter(carouselPagerAdapter);

        //TODO fixare carosello (riga 92 NullPointerException)
        /*Call<List<Recipe>> carouselCall = apiService.getRandomRecipe();
        carouselCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                carouselResult = response.body();

                carouselPagerAdapter.carouselRecipes.clear();
                carouselPagerAdapter.carouselRecipes.addAll(carouselResult);

                carouselPagerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("carouselDownload", t.getMessage());
            }
        });*/

        
        return rootView;
    }


    private void downloadBackEndInfo() {
        Call<List<Serving>> callListPortate = apiService.getPortate();
        callListPortate.enqueue(new Callback<List<Serving>>() {
            @Override
            public void onResponse(Call<List<Serving>> call, Response<List<Serving>> response) {
                listPortate = response.body();
                if(listPortate != null) {
                    homePageListAdapter.clear();
                    homePageListAdapter.addAll(listPortate);

                    homePageListAdapter.notifyDataSetChanged();
                    listView.invalidate();
                }
            }

            @Override
            public void onFailure(Call<List<Serving>> call, Throwable t) {
                Log.e("MainActivity", t.getMessage());
            }
        });



    }

}

