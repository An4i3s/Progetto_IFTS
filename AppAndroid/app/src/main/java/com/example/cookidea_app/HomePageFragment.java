package com.example.cookidea_app;

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


public class HomePageFragment extends Fragment {

    Context ctx = null;
    CarouselPagerAdapter carouselPagerAdapter;
    ViewPager carouselViewPager;

    public HomePageFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    HomePageListAdapter hPLA;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        hPLA = new HomePageListAdapter(ctx);
        ListView listView = rootView.findViewById(R.id.categoryListHomeFragment);
        listView.setAdapter(hPLA);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ctx, hPLA.getItem(position), Toast.LENGTH_LONG).show();

            }
        });

        List<String> imageURLs = new ArrayList<>();
        imageURLs.add("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/640px-Eq_it-na_pizza-margherita_sep2005_sml.jpg");
        carouselPagerAdapter = new CarouselPagerAdapter(rootView.getContext(), imageURLs);
        carouselViewPager = rootView.findViewById(R.id.carouselViewPagerHomeFragment);
        carouselViewPager.setAdapter(carouselPagerAdapter);

        return rootView;
    }


}