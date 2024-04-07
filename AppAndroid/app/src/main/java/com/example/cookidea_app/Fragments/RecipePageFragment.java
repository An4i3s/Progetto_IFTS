package com.example.cookidea_app.Fragments;

import static com.example.cookidea_app.Activities.MainActivity.BASE_URL;
import static com.example.cookidea_app.Activities.MainActivity.apiService;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.cookidea_app.Activities.CookIdeaApp;
import com.example.cookidea_app.Adapters.MenuDateSpinnerAdapter;
import com.example.cookidea_app.Adapters.HomePageListAdapter;
import com.example.cookidea_app.Adapters.MealArrayAdapter;
import com.example.cookidea_app.Backend.DownloadImageAsyncTask;
import com.example.cookidea_app.ModelClasses.Ingredients;
import com.example.cookidea_app.ModelClasses.Meal;
import com.example.cookidea_app.ModelClasses.Recipe;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecipePageFragment extends Fragment {
    Context ctx = null;
    Recipe recipe;
    View rootView = null;
    boolean favouriteChecked = false;
    User user = null;

    ImageView imageViewRecipe;
    ToggleButton favoriteButton;
    TextView textViewName, textViewTime, textViewDifficulty, textViewProvenience, textViewIngredients, textViewGuide;

    Spinner mealsSpinner;

    List<Meal> listMeals = new ArrayList<>();
    ArrayAdapter mealsAdapter;
    ArrayList<Date> dateList = new ArrayList<>();
    MenuDateSpinnerAdapter menuDateSpinnerAdapter;

    MealArrayAdapter mealsAdapter;


    public RecipePageFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe_page, container, false);
        imageViewRecipe = rootView.findViewById(R.id.imageRecipeView);
        textViewName = rootView.findViewById(R.id.nameRecipeTextView);
        textViewTime = rootView.findViewById(R.id.timeRecipeTextView);
        textViewDifficulty = rootView.findViewById(R.id.difficultyRecipeTextView);
        textViewProvenience = rootView.findViewById(R.id.provenienceRecipeTextView);
        textViewIngredients = rootView.findViewById(R.id.ingredientsRecipeTextView);
        textViewGuide = rootView.findViewById(R.id.guideRecipeTextView);
        favoriteButton = rootView.findViewById(R.id.favouriteButton);

        mealsSpinner = rootView.findViewById((R.id.mealsSpinner));
        mealsAdapter = new MealArrayAdapter(ctx, listMeals);

        mealsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealsSpinner.setAdapter(mealsAdapter);








        Bundle b = getArguments();
        String recipeId = "";

        if(b.get("recipeId") != "") {
            recipeId = b.getString("recipeId");
            downloadRecipeById(recipeId);
        }

        Date currentDate = new Date();
        dateList.add(currentDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        for(int i = 0; i <=5; i++){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date nextDate = calendar.getTime();
            dateList.add(nextDate);
        }

        menuDateSpinnerAdapter = new MenuDateSpinnerAdapter(ctx, dateList);



        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Integer> call = apiService.updatePreferito(user.getId(), recipe.getRecipeId());
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Toast.makeText(ctx, "Tutto Ok", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(ctx, "Molto Male", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    void downloadRecipeById(String recipeId){
        Call<Recipe> recipeCall = apiService.getRecipeById(recipeId);

        recipeCall.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                recipe = response.body();
                fillPage();
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.i("RecipePage", t.getMessage());
            }
        });
    }

    void fillPage(){
        String imgUrl = BASE_URL + "/static/recipes/" + recipe.getImg_name();
        new DownloadImageAsyncTask(imageViewRecipe, null).execute(imgUrl);

        user = ((CookIdeaApp)ctx.getApplicationContext()).getLoggedUser();
        if(user == null){
            favoriteButton.setVisibility(View.GONE);
        }else{
            favoriteButton.setVisibility(View.VISIBLE);

            Call<Integer> checkFavouriteCall = apiService.checkPreferito(user.getId(), recipe.getRecipeId());
            checkFavouriteCall.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Integer checkFavourite = response.body();
                    if(checkFavourite == 1){
                        favouriteChecked = true;
                    }
                    favoriteButton.setChecked(favouriteChecked);
                    favouriteChecked = false;
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });


            Call<List<Meal>> callListMeals = apiService.getMeals();
            callListMeals.enqueue(new Callback<List<Meal>>() {
                @Override
                public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                    listMeals = response.body();
                    if(listMeals != null) {
                        mealsAdapter.clear();
                        mealsAdapter.addAll(listMeals);
                        mealsAdapter.notifyDataSetChanged();

                        //mealsSpinner.invalidate();
                    }
                }

                @Override
                public void onFailure(Call<List<Meal>> call, Throwable t) {
                    Log.e("RecipePageFragment", t.getMessage());
                }
            });



        }





        textViewName.setText(recipe.getName());
        String time = " " + recipe.getTime() + "min";
        textViewTime.setText(time);

        textViewDifficulty.setText(recipe.getDifficulty());

        String provenience = " " + recipe.getProvenience();
        textViewProvenience.setText(provenience);

        String ingredients = convertIngredients();
        textViewIngredients.setText(ingredients);

        textViewGuide.setText(recipe.getGuide());
    }


    String convertIngredients(){
        List<Ingredients> recipeIngredients = recipe.getIngredients();
        String stringIngredients = "";
        for(Ingredients ingredients: recipeIngredients){
            stringIngredients += ingredients.getIngredientName() + ": " + ingredients.getIngredientQuantity() + "\n";
        }
        return stringIngredients;
    }


}