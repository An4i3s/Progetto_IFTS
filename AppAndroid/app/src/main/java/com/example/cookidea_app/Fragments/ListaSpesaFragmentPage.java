package com.example.cookidea_app.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Backend.LoginRequest;
import com.example.cookidea_app.ModelClasses.Ingredients;
import com.example.cookidea_app.R;

import java.util.ArrayList;


public class ListaSpesaFragmentPage extends Fragment {
    Context ctx = null;
    View rootView = null;

    LinearLayout layoutMonday;

    public ListaSpesaFragmentPage(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lista_spesa_page, container, false);

        layoutMonday = rootView.findViewById(R.id.layoutMonday);
        ArrayList<Ingredients> mondayIngredients = new ArrayList<>();
        // TODO: 08/04/2024 Creare tante Checkbox quando sono gli ingredienti presenti in quel giorno
        //  - trovare index ingredienti
        //  -  DB: MenuSettimale> dove data = Lunedì (da trasformare dal backend) > da li si recupera il Piatto > da id Piatto si recuperano i dati da ricettario > ingredienti
        //  - Creare un array di ingredienti
        //  - in base a Array.lenght generare i checkbox

         //   - in base a quello creare checkbox

        //add checkboxes
        for(int i = 0; i < 6; i++) {
            CheckBox cb = new CheckBox(ctx);
            //Qui set Text deve essere uguale a ingrediente, bisonga Loopare mondayIngredientsArrayList
            cb.setText("Dynamic Checkbox " + i);
            cb.setId(i+6);
            layoutMonday.addView(cb);
        }

        return rootView;
    }
}