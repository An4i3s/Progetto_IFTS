package com.example.cookidea_app.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class Ingredients {
    @SerializedName("nome_ingrediente")
    String ingredientName;
    @SerializedName("quantita_ingrediente")
    int ingredientQuantity;

    public Ingredients(String ingredientName, int ingredientQuantity) {
        this.ingredientName = ingredientName;
        this.ingredientQuantity = ingredientQuantity;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getIngredientQuantity() {
        return ingredientQuantity+"";
    }

    @Override
    public String toString() {
        return ingredientName+" - "+ingredientQuantity;
    }
}
