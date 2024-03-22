package com.example.cookidea_app;

import java.util.HashMap;

public class Recipe {
    private  int recipeId;
    private final String img_url;
    private final String name;
    private final String method;
    private final int time;
    private final int difficulty;
    private final String provenance;
    private final String serving;
    private final HashMap<String, Integer> ingredients;

    public Recipe(String name, String method, int time, int difficulty, String provenance, String serving, HashMap<String, Integer> ingredients) {
        this.img_url = "http://127.0.0.1/assets/static/" + name;
        this.name = name;
        this.method = method;
        this.time = time;
        this.difficulty = difficulty;
        this.provenance = provenance;
        this.serving = serving;
        this.ingredients = ingredients;
    }

    public String getImg_url() {
        return img_url;
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
