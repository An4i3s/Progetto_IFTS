package com.example.cookidea_app.Fragments;

import android.content.Context;
import android.os.Bundle;

import static com.example.cookidea_app.Activities.CookIdeaApp.apiService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cookidea_app.Activities.CookIdeaApp;
import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Activities.SharedPrefManager;
import com.example.cookidea_app.Adapters.ListaSpesaAdapter;
import com.example.cookidea_app.ModelClasses.Ingredients;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListaSpesaFragmentPage extends Fragment {

    Context ctx = null;
    View rootView = null;


    ArrayList<Ingredients> listaIngredients;

    ListView listaSpesaWeekLv;


    Call<List<Ingredients>> call1;

    //User user;

    public ListaSpesaFragmentPage() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(!SharedPrefManager.isLoggedIn(ctx)){
            rootView = inflater.inflate(R.layout.fragment_lista_spesa_no_logout, container, false);
            return rootView;
        }else {
            rootView = inflater.inflate(R.layout.fragment_lista_spesa_page, container, false);

        User user = ((CookIdeaApp)((MainActivity)ctx).getApplication()).getLoggedUser();


        listaIngredients = new ArrayList<>();


        call1 = apiService.getWeeklyIngredients(user.getId());


        listaSpesaWeekLv = rootView.findViewById(R.id.oneLv);
        ListaSpesaAdapter adapter1 = new ListaSpesaAdapter(ctx, listaIngredients);
        listaSpesaWeekLv.setAdapter(adapter1);


        if(listaIngredients.isEmpty()) {
            call1.enqueue(new Callback<List<Ingredients>>() {
                @Override
                public void onResponse(Call<List<Ingredients>> call, Response<List<Ingredients>> response) {
                    assert response.body() != null;
                    listaIngredients.addAll(response.body());
                    adapter1.notifyDataSetChanged();



                }

                @Override
                public void onFailure(Call<List<Ingredients>> call, Throwable t) {
                    Log.i("mondayIngredients", listaIngredients.size() + "Failure");

                }
            });

        }





        return rootView;
        }


    }




}