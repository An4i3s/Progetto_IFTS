package com.example.cookidea_app.ModelClasses;

import static com.example.cookidea_app.Activities.CookIdeaApp.BASE_URL;

import com.google.gson.annotations.SerializedName;

public class Serving {
    @SerializedName("portata")
    String serving;
    @SerializedName("urlportata")
    String imgUrl;

    public Serving(String serving) {
        this.serving = serving;
        this.imgUrl = BASE_URL + "/static/img" + serving + ".jpg";
    }

    public String getServing() {
        return serving;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
