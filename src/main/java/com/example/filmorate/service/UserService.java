package com.example.filmorate.service;

import com.example.filmorate.model.User;
import com.example.filmorate.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(int userId, int friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
        userStorage.update(user);
        userStorage.update(friend);
    }

    public void removeFriends(int userId, int friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);
        user.getFriends().remove(friendId);
        user.getFriends().remove(userId);
        userStorage.update(user);
        userStorage.update(friend);
    }

    public List<User> getCommonFriend(int userId, int otherId) {
        User user = userStorage.findById(userId);
        User other = userStorage.findById(otherId);

        return user.getFriends().stream()
                .filter(other.getFriends()::contains)
                .map(userStorage::findById)// Ищем общие ID друзей
                .collect(Collectors.toList()); // Преобразуем ID в объекты User
    }

    public List<User> findAllFriends(int id) {
        User user = userStorage.findById(id);
        return user.getFriends().stream()
                .filter(user.getFriends()::contains)
                .map(userStorage::findById)
                .collect(Collectors.toList());
    }

}
