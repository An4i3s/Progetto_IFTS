package com.example.cookidea_app.ModelClasses;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class WeeklyMenu {
    int Id;
    private Date menuDate;
    private Recipe recipe;
    private String meal;
    private String numberOfDiners;

    public WeeklyMenu(int Id, Date menuDate, Recipe recipe, String meal, String numberOfDiners) {
        this.Id = Id;
        this.menuDate = menuDate;
        this.recipe = recipe;
        this.meal = meal;
        this.numberOfDiners = numberOfDiners;
    }

    public Date getMenuDate() {
        return menuDate;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public String getMeal() {
        return meal;
    }

    public String getNumberOfDiners() {
        return numberOfDiners;
    }

    public int getId(){
        return Id;
    }
}
