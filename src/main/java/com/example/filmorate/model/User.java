package com.example.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;


public class User {
    private int id;
//    @Email(message = "Invalid email format.")
    private String email;

//    @NotNull(message = "Login cannot be null.")
    private String login;
    private String name;
//    @NotNull(message = "Date of birth cannot be null.")
//    @PastOrPresent(message = "Date of birth cannot be in the future.")
    private LocalDate birthday;


    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
