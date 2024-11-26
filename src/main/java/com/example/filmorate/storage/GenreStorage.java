package com.example.filmorate.storage;

import com.example.filmorate.model.Genre;

import java.util.List;
import com.example.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    List<Genre> findAll();
    Genre findById(int id);
    List<Genre> findAllByFilmId(int filmId);
}
