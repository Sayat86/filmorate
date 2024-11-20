package com.example.filmorate.storage;

import com.example.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    List<Film> findAll();

    Film findById(int id);

    void addLike(int userId, int filmId);

    void removeLike(int userId, int filmId);

    List<Film> findAllPopular(int count);
}
