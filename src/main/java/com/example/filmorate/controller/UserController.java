package com.example.filmorate.controller;

import com.example.filmorate.exception.InvalidEmailException;
import com.example.filmorate.exception.UserAlreadyExistException;
import com.example.filmorate.model.User;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {
    private Set<User> users = new HashSet<>();

    @GetMapping
    public Set<User> findAll() {
        return users;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        validateEmail(user.getEmail());
        if (!users.add(user)) {
            throw new UserAlreadyExistException("User with email " + user.getEmail() + " already exist");
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        validateEmail(user.getEmail());
        users.remove(user);
        users.add(user);
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return user;
    }



    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidEmailException("Email cannot be null or empty");
        }
    }
}
