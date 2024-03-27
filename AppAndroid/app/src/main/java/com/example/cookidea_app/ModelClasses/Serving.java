package com.example.cookidea_app.ModelClasses;

import static com.example.cookidea_app.Activities.MainActivity.BASE_URL;

import com.google.gson.annotations.SerializedName;

public class Serving {
    @SerializedName("portata")
    String serving;
    String imgUrl;

    public Serving(String serving) {
        this.serving = serving;
        this.imgUrl = BASE_URL + "/static/recipes" + serving + ".jpg";
    }

    public String getServing() {
        return serving;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
