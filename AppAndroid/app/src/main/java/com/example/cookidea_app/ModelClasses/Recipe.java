package com.example.cookidea_app.ModelClasses;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class Recipe {
    @SerializedName("id")
    private final int recipeId;
    @SerializedName("image_name")
    private final String img_name;
    @SerializedName("nome_piatto")
    private final String name;
    @SerializedName("procedimento")
    private final String method;
    @SerializedName("tempo")
    private final int time;
    @SerializedName("difficolta")
    private final int difficulty;
    @SerializedName("provenienza")
    private final String provenance;
    @SerializedName("portata")
    private final String serving;
    @SerializedName("ricettario")
    private final List<Ingredients> ingredients;

    public Recipe(int recipeId, String img_name, String name, String method, int time, int difficulty, String provenance, String serving, List<Ingredients> ingredients) {
        this.recipeId = recipeId;
        this.img_name = img_name;
        this.name = name;
        this.method = method;
        this.time = time;
        this.difficulty = difficulty;
        this.provenance = provenance;
        this.serving = serving;
        this.ingredients = ingredients;
    }

    public String getRecipeId(){
        return String.valueOf(recipeId);
    }
    public String getImg_name() {
        return img_name;
    }

    public String getName() {
        return name;
    }

    public String getGuide() {
        return method;
    }

    public String getTime() {
        return String.valueOf(time);
    }

    public String getDifficulty() {
        switch (difficulty){
            case 1:
                return " 😃";
            case 2:
                return " 😥";
            case 3:
                return " 🥵";
            default:
                return " Error";
        }
    }

    public String getProvenience() {
        return provenance;
    }

    public String getServing() {
        return serving;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }
}
