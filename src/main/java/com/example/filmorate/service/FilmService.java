package com.example.filmorate.service;

import com.example.filmorate.model.Film;
import com.example.filmorate.model.User;
import com.example.filmorate.storage.FilmStorage;
import com.example.filmorate.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(int filmId, int likeId) {
        Film film = filmStorage.findById(filmId);
        Film like = filmStorage.findById(likeId);
        film.getLikes().add(filmId);
        like.getLikes().add(likeId);
        filmStorage.update(film);
        filmStorage.update(like);
    }

    public void removeLike(int filmId, int likeId) {
        Film film = filmStorage.findById(filmId);
        Film like = filmStorage.findById(likeId);
        film.getLikes().remove(filmId);
        like.getLikes().remove(likeId);
        filmStorage.update(film);
        filmStorage.update(like);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.findAll().stream()
                .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
