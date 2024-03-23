package com.example.cookidea_app;


import static com.example.cookidea_app.MainActivity.retrofit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomePageFragment extends Fragment {

    Context ctx = null;
    CarouselPagerAdapter carouselPagerAdapter;
    ViewPager carouselViewPager;
    List<String> listPortate;
    List<Bitmap> listPortateImages;

    public HomePageFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    HomePageListAdapter homePageListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        CookIdeaApiEndpointInterface apiService = retrofit.create(CookIdeaApiEndpointInterface.class);

        ListView listView = rootView.findViewById(R.id.categoryListHomeFragment);

        Call<List<String>> callListPortate = apiService.getPortate();
        callListPortate.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                listPortate = response.body();
                homePageListAdapter = new HomePageListAdapter(ctx, listPortate, listPortateImages);
                listView.setAdapter(homePageListAdapter);
                homePageListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("MainActivity", t.getMessage());
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ctx, homePageListAdapter.getItem(position), Toast.LENGTH_LONG).show();

            }
        });

        List<String> imageURLs = new ArrayList<>();
        imageURLs.add("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/640px-Eq_it-na_pizza-margherita_sep2005_sml.jpg");
        imageURLs.add("https://www.biochetasi.it/wp-content/uploads/2019/09/I-bambini-e-il-cibo-spazzatura.-Meglio-non-esagerare-1-biochetasi-1000x600.jpg");
        imageURLs.add("https://inglesedinamico.net/wp-content/uploads/2021/01/cibo-in-inglese-fb.jpg");
        carouselPagerAdapter = new CarouselPagerAdapter(rootView.getContext(), imageURLs);
        carouselViewPager = rootView.findViewById(R.id.carouselViewPagerHomeFragment);
        carouselViewPager.setAdapter(carouselPagerAdapter);


        return rootView;
    }


}