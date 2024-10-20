package com.example.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private int id;
    @Email(message = "Invalid email format.")
    @NotBlank(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Login cannot be null.")
    private String login;
    private String name;
    @NotBlank(message = "Date of birth cannot be null.")
    @PastOrPresent(message = "Date of birth cannot be in the future.")
    private LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();
}
