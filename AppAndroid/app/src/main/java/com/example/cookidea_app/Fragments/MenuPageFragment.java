package com.example.cookidea_app.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookidea_app.Activities.SharedPrefManager;
import com.example.cookidea_app.Backend.LoginRequest;
import com.example.cookidea_app.R;


public class MenuPageFragment extends Fragment {

    View rootView = null;

    public MenuPageFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_menu_page, container, false);

        return rootView;
    }
}