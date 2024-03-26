package com.example.cookidea_app.Fragments;

import static com.example.cookidea_app.Activities.MainActivity.apiService;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cookidea_app.R;
import com.example.cookidea_app.ModelClasses.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchPageFragment extends Fragment {

    String searched;
    List<Recipe> results;

    public SearchPageFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_page, container, false);
        EditText editText = rootView.findViewById(R.id.searchEditText);
        Button searchButton = rootView.findViewById(R.id.startSearchButton);
        ListView resultListView = rootView.findViewById(R.id.serachResultListView);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searched = s.toString();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Recipe>> callSearchRecipe = apiService.getRecipeByName(searched);
                callSearchRecipe.enqueue(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        results = response.body();
                        //probabilmente errato
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {

                    }
                });
            }
        });
        return rootView;
    }
}