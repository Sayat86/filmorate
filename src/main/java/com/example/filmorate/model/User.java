package com.example.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    private int id;
    @Email(message = "Invalid email format.")
    @NotBlank(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Login cannot be null.")
    private String login;
    private String name;
    @NotNull(message = "Date of birth cannot be null.")
    @PastOrPresent(message = "Date of birth cannot be in the future.")
    private LocalDate birthday;

//    private final Set<Integer> friends = new HashSet<>();
}
