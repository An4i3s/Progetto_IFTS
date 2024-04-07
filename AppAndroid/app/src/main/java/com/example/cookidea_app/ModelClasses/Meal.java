package com.example.cookidea_app.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class Meal {

    @SerializedName("id")
    int id;
    @SerializedName("nome_tipo_pasto")
    String mealName;


    public Meal(int id, String mealName) {
        this.id = id;
        this.mealName = mealName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }


    @Override
    public String toString() {
        return mealName;
    }
}
