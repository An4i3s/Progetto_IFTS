package com.example.cookidea_app.Fragments;


import static com.example.cookidea_app.Activities.CookIdeaApp.apiService;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cookidea_app.Activities.CookIdeaApp;
import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Activities.SharedPrefManager;
import com.example.cookidea_app.Adapters.MenuListAdapter;
import com.example.cookidea_app.Adapters.SearchPageListAdapter;
import com.example.cookidea_app.Backend.LoginRequest;
import com.example.cookidea_app.ModelClasses.Recipe;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.ModelClasses.WeeklyMenu;
import com.example.cookidea_app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class MenuPageFragment extends Fragment {

    Context ctx = null;
    List<WeeklyMenu> results = new ArrayList<>();
    StickyListHeadersListView stickyListView;
    MenuListAdapter menuListAdapter;
    View rootView = null;

    User user = null;
    public MenuPageFragment() {

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

        if (!SharedPrefManager.isLoggedIn(ctx)){
            rootView = inflater.inflate(R.layout.fragment_menu_page_no_login, container, false);
            return rootView;
        }else {
            rootView = inflater.inflate(R.layout.fragment_menu_page, container, false);


        stickyListView = (StickyListHeadersListView) rootView.findViewById(R.id.weeklyMenuList);
        menuListAdapter = new MenuListAdapter(ctx, results);
        stickyListView.setAdapter(menuListAdapter);

        stickyListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                menuListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        user = ((CookIdeaApp)ctx.getApplicationContext()).getLoggedUser();
        downloadBackEndInfo();
        return rootView;
        }
    }

    private void downloadBackEndInfo() {


        Call<List<WeeklyMenu>> listCall = apiService.getWeeklyMenu(user.getId());

        listCall.enqueue(new Callback<List<WeeklyMenu>>() {
            @Override
            public void onResponse(Call<List<WeeklyMenu>> call, Response<List<WeeklyMenu>> response) {
                results = response.body();
                if (results != null) {
                    menuListAdapter.weeklyMenus.addAll(results);
                    menuListAdapter.notifyDataSetChanged();
                    stickyListView.invalidate();
                }

            }

            @Override
            public void onFailure(Call<List<WeeklyMenu>> call, Throwable t) {
                Log.i("MenuPageFragment", t.getMessage());
            }
        });

    }
}