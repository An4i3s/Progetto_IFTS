package com.example.cookidea_app.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookidea_app.Activities.CookIdeaApp;
import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Activities.SharedPrefManager;
import com.example.cookidea_app.Backend.CookIdeaApiEndpointInterface;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;

import org.w3c.dom.Text;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfiloFragment extends Fragment {

    User user;

    TextView usernameTV;
    TextView nome;
    Button modNome;
    SharedPreferences sharedPreferences;

    Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    CookIdeaApiEndpointInterface endpointInterface = retrofit.create(CookIdeaApiEndpointInterface.class);

    Context ctx;


    public ProfiloFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profilo, container, false);


       User u = ((CookIdeaApp)((MainActivity)ctx).getApplication()).getLoggedUser();
        usernameTV = rootView.findViewById(R.id.usernameTV);
        usernameTV.setText(u.getUsername());

        return rootView;
    }
}
