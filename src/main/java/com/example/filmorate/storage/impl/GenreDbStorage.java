package com.example.filmorate.storage.impl;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.model.Genre;
import com.example.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> findAll() {
        String sql = "select * from genres";
        return jdbcTemplate.query(sql, this::rowMapper);
    }

    @Override
    public Genre findById(int id) {
        String sql = "select * from genres where id = ?";
        return jdbcTemplate.query(sql, this::rowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Жанр с id=%s не найден".formatted(id)));
    }

    @Override
    public List<Genre> findAllByFilmId(int filmId) {
        String sql = """
                select g.*
                from genres g
                join public.film_genres fg on g.id = fg.genre_id
                where fg.film_id = ?""";
        return jdbcTemplate.query(sql, this::rowMapper, filmId);
    }

    private Genre rowMapper(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Genre(id, name);
    }
}
