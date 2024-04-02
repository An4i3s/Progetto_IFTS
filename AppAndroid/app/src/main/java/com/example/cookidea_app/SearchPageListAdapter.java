package com.example.cookidea_app;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class SearchPageListAdapter extends ArrayAdapter<Recipe> {
    List<Recipe> recipes;


    public SearchPageListAdapter(@NonNull Context context, @NonNull List<Recipe> objects) {
        super(context, R.layout.search_page_recipe_list, objects);
    }
}
