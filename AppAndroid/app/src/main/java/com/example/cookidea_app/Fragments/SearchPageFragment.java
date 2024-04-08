package com.example.cookidea_app.Fragments;

import static com.example.cookidea_app.Activities.MainActivity.apiService;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cookidea_app.Activities.CookIdeaApp;
import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Adapters.SearchPageListAdapter;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;
import com.example.cookidea_app.ModelClasses.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchPageFragment extends Fragment {

    Context ctx = null;
    List<Recipe> results = new ArrayList<>();

    static EditText searchEditText;
    ListView resultListView;
    SearchPageListAdapter searchPageListAdapter;
    Button searchButton;
    View rootView = null;

    long loggedUserID = 0;


    public SearchPageFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        results = new ArrayList<>();

        rootView = inflater.inflate(R.layout.fragment_search_page, container, false);
        searchEditText = rootView.findViewById(R.id.searchEditText);
        searchButton = rootView.findViewById(R.id.startSearchButton);
        resultListView = rootView.findViewById(R.id.serachResultListView);
        searchPageListAdapter = new SearchPageListAdapter(ctx, results);
        resultListView.setAdapter(searchPageListAdapter);


        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
            }
        });



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchEditTextStr = searchEditText.getText().toString();
                if(!searchEditTextStr.isEmpty()) {
                    searchPageListAdapter.clear();
                    downloadBackEndInfo(searchEditText.getText().toString(), loggedUserID,0);
                }
            }
        });

        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ctx, searchPageListAdapter.getItem(position).getName(), Toast.LENGTH_LONG).show();
                ((MainActivity)ctx).changeTabById(R.id.searchPage, searchPageListAdapter.getItem(position).getRecipeId());
                ((MainActivity)ctx).changeFrameByNavigationTab(R.id.recipePage);
            }
        });

        return rootView;


    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle b = getArguments();
        String category = "";

        User user = ((CookIdeaApp)((MainActivity)ctx).getApplication()).getLoggedUser();
        if (user != null)
            loggedUserID = user.getId();

        if(b.get("filterByCategory") != "") {
            category = b.getString("filterByCategory");
            downloadBackEndInfo(category, loggedUserID, 1);
        }
    }

    private void downloadBackEndInfo(String filter, long loggedUserID, int function){
        Call<List<Recipe>> listCall = null;
        switch (function){
            case 0:
                listCall = apiService.getRecipeByName(filter, loggedUserID);
                break;
            case 1:
                searchEditText.setText("");
                listCall = apiService.getRecipeByServing(filter);
                break;
        }
        listCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                results = response.body();
                if(results != null) {
                    searchPageListAdapter.addAll(results);
                    searchPageListAdapter.notifyDataSetChanged();
                    resultListView.invalidate();
                }

            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("RicercaRicettaUtente", t.getMessage());
            }
        });
    }


}