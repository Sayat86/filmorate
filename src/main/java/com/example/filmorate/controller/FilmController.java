package com.example.filmorate.controller;

import com.example.filmorate.exception.MaxLengthException;
import com.example.filmorate.exception.NegativeDurationException;
import com.example.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {
    private Set<Film> films = new HashSet<>();

    @GetMapping
    public Set<Film> findAll() {
        return films;
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        maxDescription(film.getDescription());
        positiveDuration(film.getDuration());
        setReleaseDate(film.getReleaseDate());
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        films.remove(film);
        films.add(film);
        maxDescription(film.getDescription());
        positiveDuration(film.getDuration());
        setReleaseDate(film.getReleaseDate());
        return film;
    }

    private void maxDescription(String description) {
        if (description.length() > 200) {
            throw new MaxLengthException("Длина не может быть больше 200 символов");
        }
    }

    private void positiveDuration(int duration) {
        if (duration < 0) {
            throw new NegativeDurationException("Продолжительность должна быть положительной");
        }
    }

    private void setReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new IllegalArgumentException("Release date cannot be earlier than December 28, 1895");
        }
    }

}
