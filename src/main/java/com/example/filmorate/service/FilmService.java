package com.example.filmorate.service;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.exception.ValidationException;
import com.example.filmorate.model.Film;
import com.example.filmorate.model.Genre;
import com.example.filmorate.model.User;
import com.example.filmorate.storage.FilmStorage;
import com.example.filmorate.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film create(Film film) {
        setReleaseDate(film.getReleaseDate());
        for (Genre genre : film.getGenres()) {
            if (genre.getId() > 6 || genre.getId() < 1) {
                throw new ValidationException("Жанр с таким ID не найден");
            }
            // TODO: проверить находится айди жанра от 1 до 6
            // если не находится выброс исключения ValidationException
        }
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        int filmId = film.getId();
        findById(filmId);
        return filmStorage.update(film);
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findById(int id) {
        Film film = filmStorage.findById(id);
        if (film == null) {
            throw new NotFoundException("Фильм с таким ID не найден");
        }
        return film;
    }

    public void addLike(int filmId, int userId) {
        Film film = findById(filmId);
        User user = userStorage.findById(userId);
        filmStorage.addLike(userId, filmId);
    }

    public void removeLike(int filmId, int userId) {
        Film film = findById(filmId);
        User user = userStorage.findById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с таким ID не найден");
        }
        filmStorage.removeLike(userId, filmId);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.findAllPopular(count);
    }

    private void setReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Release date cannot be earlier than December 28, 1895");
        }
    }
}
