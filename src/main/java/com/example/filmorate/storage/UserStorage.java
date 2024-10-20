package com.example.filmorate.storage;

import com.example.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User create(User user);
    User update(User user);
    List<User> findAll();
    User findById(int id);
}
