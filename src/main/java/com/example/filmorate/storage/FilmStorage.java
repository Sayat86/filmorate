package com.example.filmorate.storage;

import com.example.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film create(Film film);
    Film update(Film film);
    List<Film> findAll();
    Film findById(int id);
}
