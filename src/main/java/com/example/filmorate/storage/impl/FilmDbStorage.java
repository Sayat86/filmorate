package com.example.filmorate.storage.impl;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.exception.ValidationException;
import com.example.filmorate.model.Film;
import com.example.filmorate.model.Genre;
import com.example.filmorate.model.Mpa;
import com.example.filmorate.storage.FilmStorage;
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
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreDbStorage genreDbStorage;

    private static final String SELECT = """
            select f.id as film_id,
            f.name as film_name,
            f.description as film_description,
            f.release_date as film_release_date,
            f.duration as film_duration,
            f.mpa_id as mpa_id,
            m.name as mpa_name
            from films f
            join mpa m on m.id = f.mpa_id""";

    @Override
    public Film create(Film film) {
        int mpaId = film.getMpa().getId();
        if (mpaId > 5 || mpaId < 1){
            throw new ValidationException("Mpa не найден");
        }
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> map = Map.of("name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "duration", film.getDuration(),
                "mpa_id", mpaId);
        int id = insert.executeAndReturnKey(map).intValue();
        film.setId(id);

        for (Genre genre : film.getGenres()) {
            String sql = "insert into film_genres(film_id, genre_id) values (?, ?)";
            jdbcTemplate.update(sql, film.getId(), genre.getId());
        }
        return film;
    }

    @Override
    public Film update(Film film) {
        jdbcTemplate.update("update films set name = ?, description = ?, release_date = ?," +
                        "duration = ? where id = ?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId());
        return film;
    }

    @Override
    public List<Film> findAll() {
        return jdbcTemplate.query(SELECT+" order by f.id", this::rowMapper);
    }

    @Override
    public Film findById(int id) {
        String sql = SELECT + " where f.id = ?";
        return jdbcTemplate.query(sql, this::rowMapper, id)
                .stream()
                .findFirst().orElseThrow(() -> new NotFoundException("Пользователь с id=%s не найден".formatted(id)));
    }

    @Override
    public void addLike(int userId, int filmId) {
        String sql = "insert into likes(user_id, film_id) values (?, ?)";
        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public void removeLike(int userId, int filmId) {
        String sql = "delete from likes where user_id = ? and film_id = ?";
        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public List<Film> findAllPopular(int count) {
        String sql = SELECT + """
                 left join likes l on f.id = l.film_id
                group by f.id, m.name
                order by count(l.film_id) desc
                limit ?""";
        return jdbcTemplate.query(sql, this::rowMapper, count);
    }

    private Film rowMapper(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("film_id");
        String name = rs.getString("film_name");
        String description = rs.getString("film_description");
        LocalDate releaseDate = rs.getDate("film_release_date").toLocalDate();
        int duration = rs.getInt("film_duration");

        int mpaId = rs.getInt("mpa_id");
        String mpaName = rs.getString("mpa_name");
        Mpa mpa = new Mpa(mpaId, mpaName);

        List<Genre> genreList = genreDbStorage.findAllByFilmId(id);
        Film film = new Film(id, name, description, releaseDate, duration, mpa);
        film.getGenres().addAll(genreList);
        return film;
    }
}
