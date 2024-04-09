package com.example.cookidea_app.Activities;

import static com.example.cookidea_app.Activities.CookIdeaApp.apiService;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.cookidea_app.Backend.CookIdeaApiEndpointInterface;
import com.example.cookidea_app.Backend.LoginRequest;
import com.example.cookidea_app.Fragments.DatePickerDialogFragment;
import com.example.cookidea_app.Fragments.HomePageFragment;
import com.example.cookidea_app.Fragments.ListaSpesaFragmentPage;
import com.example.cookidea_app.Fragments.LoginFragment;
import com.example.cookidea_app.Fragments.MenuPageFragment;
import com.example.cookidea_app.Fragments.PasswordFragment;
import com.example.cookidea_app.Fragments.ProfiloFragment;
import com.example.cookidea_app.Fragments.RecipePageFragment;
import com.example.cookidea_app.Fragments.RicettePreferiteFragment;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;
import com.example.cookidea_app.Fragments.RegistrazioneFragment;
import com.example.cookidea_app.Fragments.SearchPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity
        implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener,
        DatePickerDialog.OnDateSetListener {

    /*
    toggle button ricetta singola per preferiti //edo
    layout pagina lista della spesa //edo
    collegamento lista della spesa al backend
    layout ricette preferite //anais
    collegamento ricette preferite al backend
    layout menu settimanale //edo
    collegamento menu settimanale al backend
    implementare fragment profiloUtente //anais
    collegre pagina profile utente con dati presi da db //anais
    per il momento niente SQLite
    */


    //TODO fixare immagini errate nella ricerca
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    HomePageFragment homeFragment = new HomePageFragment();
    SearchPageFragment searchFragment = new SearchPageFragment();
    ListaSpesaFragmentPage listaSpesaFragment = new ListaSpesaFragmentPage();
    MenuPageFragment menuFragment = new MenuPageFragment();
    LoginFragment loginFragment = new LoginFragment();
    ProfiloFragment profiloFragment = new ProfiloFragment();
    RicettePreferiteFragment ricettePreferFrag = new RicettePreferiteFragment();
    RecipePageFragment recipePageFragment = new RecipePageFragment();
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    List<String> listPortate;
    TextView etaTv;
    DatePickerDialogFragment datePicker;
    String search = "";
    SharedPreferences sharedPreferences;
    User loggedUser = null;


    public void onLoginSuccess(User user) {
        ((CookIdeaApp) getApplication()).setLoggedUser(user);
        loggedUser = user;
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottomNavigationView = findViewById((R.id.bottomNavBar));
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.homePage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");


        drawerLayout = findViewById(R.id.drawerLayout);
        ImageView openDrwImg = findViewById(R.id.openDrawerImage);
        openDrwImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        etaTv = findViewById(R.id.tvEta);
        datePicker = new DatePickerDialogFragment();
        datePicker.onCreateDialog(savedInstanceState);

        sharedPreferences = getSharedPreferences(SharedPrefManager.PREF_NAME, Context.MODE_PRIVATE);
        updateNavigationDrawer();


    }

    public void changeTabById(int res, String search) { //1
        this.search = search;
        bottomNavigationView.setSelectedItemId(res);
    }

    public void changeFrameByNavigationTab(int id_tab) { //3
        Fragment fragment = null;

        if (id_tab == R.id.homePage)
            fragment = homeFragment;
        if (id_tab == R.id.searchPage) {
            Bundle b = new Bundle();
            b.putString("filterByCategory", search);
            fragment = searchFragment;
            fragment.setArguments(b);
        }
        if (id_tab == R.id.recipePage) {
            Bundle b = new Bundle();
            b.putString("recipeId", search);
            fragment = recipePageFragment;
            fragment.setArguments(b);
        }
        if (id_tab == R.id.shoppingListPage)
            fragment = listaSpesaFragment;
        if (id_tab == R.id.menuPage)
            fragment = menuFragment;
        if (id_tab == R.id.loginPage)
            fragment = loginFragment;
        if (id_tab == R.id.userProfilePage)
            fragment = profiloFragment;
        if (id_tab == R.id.ricettePrefe)
            fragment = ricettePreferFrag;
        if (id_tab == R.id.logout) {
            SharedPrefManager.logout(this);
            loggedUser = null;
            ((CookIdeaApp) getApplication()).logout();
            updateNavigationDrawer();
            fragment = homeFragment;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) { //2
        int id = menuItem.getItemId();
        changeFrameByNavigationTab(id);

        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }


    public void apriRegistrazione() {

        Fragment fragment = new RegistrazioneFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContent, fragment).commit();

    }

    public void apriPasswordFragmnent() {

        Fragment fragment = new PasswordFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContent, fragment).commit();

    }


    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        // RegistrazioneFragment registrazioneFragment = new RegistrazioneFragment();
        // registrazioneFragment.onDateSet(view, year, month, dayOfMonth);
    }

    public void updateNavigationDrawer() {
        boolean isLoggedIn = SharedPrefManager.isLoggedIn(this);
        if (isLoggedIn && loggedUser == null) {
            LoginRequest loginRequest = SharedPrefManager.getLoginRequestFromSharedPref(this);
            Call<User> call = apiService.login(loginRequest);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        loggedUser = response.body();
                        onLoginSuccess(loggedUser); // da lasciare
                    } else {
                        Toast.makeText(MainActivity.this, "Utente non Trovato", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.i("Errore login", "Errore API login");
                }
            });
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_nav_menu_login);

        } else if (isLoggedIn){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_nav_menu_login);

        }else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_nav_menu);
        }

    }


}