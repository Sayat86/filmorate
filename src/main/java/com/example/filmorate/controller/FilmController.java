package com.example.filmorate.controller;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.exception.ValidationException;
import com.example.filmorate.model.Film;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/films")
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    private int uniqueId = 1;

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        setReleaseDate(film.getReleaseDate());
        film.setId(uniqueId++);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Такой ID не существует");
        }
        setReleaseDate(film.getReleaseDate());
        films.put(film.getId(), film);
        return film;
    }


    private void setReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Release date cannot be earlier than December 28, 1895");
        }
    }

}
