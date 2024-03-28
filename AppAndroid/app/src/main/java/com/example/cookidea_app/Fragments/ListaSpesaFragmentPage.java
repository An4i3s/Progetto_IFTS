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

import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Backend.LoginRequest;
import com.example.cookidea_app.R;


public class ListaSpesaFragmentPage extends Fragment {
    Context ctx = null;
    View rootView = null;


    public ListaSpesaFragmentPage(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lista_spesa_page, container, false);


        return rootView;
    }
}