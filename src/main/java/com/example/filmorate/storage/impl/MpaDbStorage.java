package com.example.filmorate.storage.impl;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.model.Mpa;
import com.example.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> findAll() {
        String sql = "select * from mpa";
        return jdbcTemplate.query(sql, this::rowMapper);
    }

    @Override
    public Mpa findById(int id) {
        String sql = "select * from mpa where id = ?";
        return jdbcTemplate.query(sql, this::rowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Mpa с id=%sне найден".formatted(id)));
    }

    private Mpa rowMapper(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Mpa(id, name);
    }
}
