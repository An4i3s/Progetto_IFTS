package com.example.cookidea_app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookidea_app.ModelClasses.Recipe;
import com.example.cookidea_app.R;

import java.util.List;

public class RicettePreferiteFragment extends Fragment {

    ListView preferitiLV;

    List<Recipe> listaRicettePreferite;

    public RicettePreferiteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_ricette_preferite, container, false);

        preferitiLV = rootview.findViewById(R.id.ricettePrefeListView);

        return rootview;
    }
}
