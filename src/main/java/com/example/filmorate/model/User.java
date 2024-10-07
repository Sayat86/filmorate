package com.example.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private int id;
    @Email(message = "Invalid email format.")
    private String email;

    @NotNull(message = "Login cannot be null.")
    private String login;
    private String name;
    @NotNull(message = "Date of birth cannot be null.")
    @PastOrPresent(message = "Date of birth cannot be in the future.")
    private LocalDate birthday;


}
