package com.example.cookidea_app.ModelClasses;

import java.util.Date;

public class User {
    private final String name;
    private final String surname;
    private final String username;
    private final Date birthdate;
    private final String email;

    public User(String name, String surname, String username, String email, Date birthdate) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.birthdate = birthdate;
        this.email = email;
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
}
