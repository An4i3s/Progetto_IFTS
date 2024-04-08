package com.example.cookidea_app.ModelClasses;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class WeeklyMenu {
    @SerializedName("id")
    int Id;
    @SerializedName("data")
    private Date menuDate;
    @SerializedName("image_name")
    private String recipeImg;
    @SerializedName("id_piatto")
    private int recipeId;
    @SerializedName("nome_piatto")
    private String recipeName;
    @SerializedName("nome_tipo_pasto")
    private String meal;
    private String numberOfDiners;

    public WeeklyMenu(int id, Date menuDate, String recipeImg, int recipeId, String recipeName, String meal, String numberOfDiners) {
        Id = id;
        this.menuDate = menuDate;
        this.recipeImg = recipeImg;
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.meal = meal;
        this.numberOfDiners = numberOfDiners;
    }

    public int getId() {
        return Id;
    }

    public Date getMenuDate() {
        return menuDate;
    }

    public String getRecipeImg() {
        return recipeImg;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getMeal() {
        return meal;
    }

    public String getNumberOfDiners() {
        return numberOfDiners;
    }
}
