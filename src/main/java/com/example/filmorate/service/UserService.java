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
        User user = findById(userId);
        User friend = findById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void removeFriends(int userId, int friendId) {
        User user = findById(userId);
        User friend = findById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> getCommonFriend(int userId, int otherId) {
        User user = findById(userId);
        User other = findById(otherId);

        return user.getFriends().stream()
                .filter(other.getFriends()::contains)
                .map(userStorage::findById)// Ищем общие ID друзей
                .collect(Collectors.toList()); // Преобразуем ID в объекты User
    }

    public List<User> findAllFriends(int id) {
        User user = findById(id);
        return user.getFriends().stream()
                .filter(user.getFriends()::contains)
                .map(userStorage::findById)
                .collect(Collectors.toList());
    }

}
