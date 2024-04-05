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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.cookidea_app.Activities.CookIdeaApp;
import com.example.cookidea_app.Backend.DownloadImageAsyncTask;
import com.example.cookidea_app.ModelClasses.Ingredients;
import com.example.cookidea_app.ModelClasses.Recipe;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;

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

        Bundle b = getArguments();
        String recipeId = "";

        if(b.get("recipeId") != "") {
            recipeId = b.getString("recipeId");
            downloadRecipeById(recipeId);
        }

        favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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