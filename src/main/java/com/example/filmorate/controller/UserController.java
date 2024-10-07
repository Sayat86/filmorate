package com.example.filmorate.controller;
import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.model.User;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
@Validated
@RequestMapping("/users")
public class UserController {
    private Map<Integer, User> users = new HashMap<>();
    private int uniqueId = 1;

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(uniqueId++);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        System.out.println(user);
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Такой ID не существует");
        }
        users.put(user.getId(), user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }

}
