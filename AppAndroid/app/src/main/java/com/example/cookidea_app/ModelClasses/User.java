package com.example.cookidea_app.ModelClasses;

import com.google.gson.annotations.SerializedName;


import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class User implements Serializable {
    private long id;
    @SerializedName("nome")
    private  String name;
    @SerializedName("cognome")
    private  String surname;
    @SerializedName("username")
    private final String username;
    @SerializedName("data_nascita")
    private  Date birthdate;
    private final String email;
    @SerializedName("password")
    private String password;


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

    public String getDate2() {
        String dateFormat = DateFormat.getDateInstance().format(birthdate);

        return dateFormat;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }




    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
