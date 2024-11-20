package com.example.filmorate.storage.impl;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.model.User;
import com.example.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> map = Map.of("name", user.getName(),
                "email", user.getEmail(),
                "login", user.getLogin(),
                "birthday", user.getBirthday());
        int id = insert.executeAndReturnKey(map).intValue();
        user.setId(id);
        return user;
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update("update users set email = ?, login = ?, name = ?, birthday = ?" +
                        "where id = ?",
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, this::rowMapper);
    }

    @Override
    public User findById(int id) {
        String sql = "select * from users where id = ?";
        return jdbcTemplate.query(sql, this::rowMapper, id)
                .stream()
                .findFirst().orElseThrow(() -> new NotFoundException("Пользователь с id=%sне найден".formatted(id)));
    }

    @Override
    public void addFriend(int userId, int friendId) {
        String sql = "insert into friendships(user_id, user2_id) values (?, ?)";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        String sql = "delete from friendships where user_id = ? and user2_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public List<User> findAllFriends(int userId) {
        String sql = """
                select u.*
                from users u
                join friendships f on u.id = f.user2_id
                where f.user_id = ?""";
        return jdbcTemplate.query(sql, this::rowMapper, userId);
    }

    @Override
    public List<User> findAllCommonFriends(int userId, int friendId) {
        String sql = "select * from users u  " +
                "join friendships f1 on (u.id = f1.friendId) " +
                "join friendships f2 on (u.id = f2.friendId) " +
                "where f1.userId = ? and f2.userId = ? " +
                "and f1.friendId = f2.friendId";
        return jdbcTemplate.query(sql, this::rowMapper, userId, friendId);
    }

    private User rowMapper(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        return new User(id, email, login, name, birthday);
    }
}
