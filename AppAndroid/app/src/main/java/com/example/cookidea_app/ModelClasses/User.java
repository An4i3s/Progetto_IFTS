package com.example.cookidea_app.ModelClasses;

import com.google.gson.annotations.SerializedName;


import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private long id;
    @SerializedName("nome")
    private final String name;
    @SerializedName("cognome")
    private final String surname;
    @SerializedName("username")
    private final String username;
    @SerializedName("data_nascita")
    private final Date birthdate;
    private final String email;
    @SerializedName("password")
    private final String password;


    public User(long id, String name, String surname, String username, String email, Date birthdate, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
    }
    public User(String name, String surname, String username, String email, Date birthdate, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
    }


    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public Date getDate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


}
