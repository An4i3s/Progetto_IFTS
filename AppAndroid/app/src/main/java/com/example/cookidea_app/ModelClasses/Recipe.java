package com.example.cookidea_app.ModelClasses;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Recipe {
    @SerializedName("id")
    private final int recipeId;
    @SerializedName("image_name")
    private final String img_name;
    @SerializedName("nome_piatto")
    private final String name;
    private final String method;
    @SerializedName("tempo")
    private final int time;
    @SerializedName("difficolta")
    private final int difficulty;
    @SerializedName("provenienza")
    private final String provenance;
    @SerializedName("portata")
    private final String serving;
    private final HashMap<String, Integer> ingredients;

    public Recipe(int recipeId, String img_name, String name, String method, int time, int difficulty, String provenance, String serving, HashMap<String, Integer> ingredients) {
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

    public int getRecipeId(){
        return recipeId;
    }
    public String getImg_name() {
        return img_name;
    }

    public String getName() {
        return name;
    }

    public String getMethod() {
        return method;
    }

    public String getTime() {
        return String.valueOf(time);
    }

    public String getDifficulty() {
        switch (difficulty){
            case 1:
                return "😃";
            case 2:
                return "😥";
            case 3:
                return "🥵";
            default:
                return "NoDifficulty";
        }
    }

    public String getProvenance() {
        return provenance;
    }

    public String getServing() {
        return serving;
    }

    public HashMap<String, Integer> getIngredients() {
        return ingredients;
    }
}
