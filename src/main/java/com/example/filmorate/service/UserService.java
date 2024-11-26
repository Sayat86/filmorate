package com.example.filmorate.service;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.model.User;
import com.example.filmorate.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        int userId = user.getId();
        findById(userId);
        return userStorage.update(user);
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User findById(int id) {
        User user = userStorage.findById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с таким ID не найден");
        }
        return user;
    }

    public void addFriend(int userId, int friendId) {
        findById(userId);
        findById(friendId);
        userStorage.addFriend(userId, friendId);
    }

    public void removeFriends(int userId, int friendId) {
        findById(userId);
        findById(friendId);
        userStorage.removeFriend(userId, friendId);
    }

    public List<User> getCommonFriend(int userId, int otherId) {
        User user = findById(userId);
        User other = findById(otherId);

        return userStorage.findAllCommonFriends(userId, otherId);
    }

    public List<User> findAllFriends(int id) {
        findById(id);
        return userStorage.findAllFriends(id);
    }
}
