package com.example.cookidea_app;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
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

    public static final String BASE_URL = "http://192.168.1.141:8000";
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

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
        //questo metodo controlla solo la bottom navigation bar, i fragment per il profilo richiedono un metodo a parte

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
        //? replace con loginPage o mai content
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContent, fragment).commit();
       // DrawerLayout drawer = findViewById(R.id.drawerLayout);
        //drawer.closeDrawer(GravityCompat.END);
    }
}