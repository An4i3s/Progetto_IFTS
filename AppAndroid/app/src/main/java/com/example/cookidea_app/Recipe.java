package com.example.cookidea_app;

import java.util.HashMap;

public class Recipe {
    private  int recipeId;
    private final String img_name;
    private final String name;
    private final String method;
    private final int time;
    private final int difficulty;
    private final String provenance;
    private final String serving;
    private final HashMap<String, Integer> ingredients;

    public Recipe(String img_name, String name, String method, int time, int difficulty, String provenance, String serving, HashMap<String, Integer> ingredients) {
        this.img_name = img_name;
        this.name = name;
        this.method = method;
        this.time = time;
        this.difficulty = difficulty;
        this.provenance = provenance;
        this.serving = serving;
        this.ingredients = ingredients;
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

    public int getTime() {
        return time;
    }

    public int getDifficulty() {
        return difficulty;
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
