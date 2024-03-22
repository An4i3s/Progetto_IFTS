package com.example.cookidea_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class SearchPageFragment extends Fragment {

    public SearchPageFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_page, container, false);
        EditText editText = rootView.findViewById(R.id.searchEditText);
        Button searchButton = rootView.findViewById(R.id.startSearchButton);
        ListView resultListView = rootView.findViewById(R.id.serachResultListView);
        return rootView;
    }
}