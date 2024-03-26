package com.example.cookidea_app.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.cookidea_app.Backend.CookIdeaApiEndpointInterface;
import com.example.cookidea_app.Fragments.DatePickerDialogFragment;
import com.example.cookidea_app.Fragments.HomePageFragment;
import com.example.cookidea_app.Fragments.ListaSpesaFragmentPage;
import com.example.cookidea_app.Fragments.LoginFragment;
import com.example.cookidea_app.Fragments.MenuPageFragment;
import com.example.cookidea_app.R;
import com.example.cookidea_app.Fragments.RegistrazioneFragment;
import com.example.cookidea_app.Fragments.SearchPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener   {
    /*fare activity di login
    implementare splashpage
    collegare gli endpoint
    modificare risorse homePageFragment con dati presi da db
    implementare funzione di login
    implementare funzione di logout
    implementare fragment profiloUtente
    collegre pagina profile utente con dati presi da db
    per il momento niente SQLite*/
    BottomNavigationView bottomNavigationView;
    HomePageFragment homeFragment = new HomePageFragment();
    SearchPageFragment searchFragment = new SearchPageFragment();
    ListaSpesaFragmentPage listaSpesaFragment = new ListaSpesaFragmentPage();
    MenuPageFragment menuFragment = new MenuPageFragment();
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    List<String> listPortate;
    TextView etaTv;
    DatePickerDialogFragment datePicker;


    public static final String BASE_URL = "http://192.168.52.85:8000";
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final CookIdeaApiEndpointInterface apiService = retrofit.create(CookIdeaApiEndpointInterface.class);


    Button registraBtn;


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

        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        etaTv = findViewById(R.id.tvEta);
        datePicker = new DatePickerDialogFragment();
        datePicker.onCreateDialog(savedInstanceState);



    }




   @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       return true;
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        int id = menuItem.getItemId();

        if (id == R.id.homePage)
            fragment = homeFragment;
        if (id == R.id.searchPage)
            fragment = searchFragment;
        if (id == R.id.shoppingListPage)
            fragment = listaSpesaFragment;
        if (id == R.id.menuPage)
            fragment = menuFragment;
        if (id == R.id.loginPage)
            fragment = new LoginFragment();
        //if(id==R.id.) AggiungereFragment x Profilo

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();

       DrawerLayout drawer = findViewById(R.id.drawerLayout);
       drawer.closeDrawer(GravityCompat.END);
        return true;
    }


    public void apriRegistrazione(){

        Fragment fragment = new RegistrazioneFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContent, fragment).commit();

    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
       // RegistrazioneFragment registrazioneFragment = new RegistrazioneFragment();
       // registrazioneFragment.onDateSet(view, year, month, dayOfMonth);
    }


}