package com.example.filmorate.controller;

import com.example.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

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
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        films.remove(film);
        films.add(film);
        return film;
    }

}
