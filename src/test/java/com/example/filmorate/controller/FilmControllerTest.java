package com.example.filmorate.controller;

import com.example.filmorate.exception.ValidationException;
import com.example.filmorate.model.Film;
import com.example.filmorate.storage.InMemoryFilmStorage;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void correctDataTest() {
        Film film = new Film();
        film.setName("Sara");
        film.setDescription("Track");
        film.setReleaseDate(LocalDate.of(1800, 12, 10));
        film.setDuration(60);
        String expected2 = "Release date cannot be earlier than December 28, 1895";

        InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
        Executable executable = () -> filmStorage.create(film);

        ValidationException ex = assertThrows(ValidationException.class, executable);
        assertEquals(expected2, ex.getMessage());
    }

    @Test
    void correctDescriptionTest() {
        Film film = new Film();
        film.setName("Sara");
        film.setDescription("Track Release date cannot be earlier than December 28, 1895, " +
                "Release date cannot be earlier than December 28, 1895," +
                "Release date cannot be earlier than December 28, 1895," +
                "Release date cannot be earlier than December 28, 1895," +
                "Release date cannot be earlier than December 28, 1895," +
                "Release date cannot be earlier than December 28, 1895");
        film.setReleaseDate(LocalDate.of(2022, 12, 10));
        film.setDuration(60);
        String expected2 = "Длина не может превышать 200 символов";

        String actual = validateAndGetFirstMessageTemplate(film);

        Assertions.assertEquals(expected2, actual);
    }

    @Test
    void negativeDurationTest() {
        Film film = new Film();
        film.setName("Sara");
        film.setDescription("Track");
        film.setReleaseDate(LocalDate.of(2020, 12, 10));
        film.setDuration(-10);

        String expected = "Must be greater than 0";

        String actual = validateAndGetFirstMessageTemplate(film);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void emptyNameTest() {
        Film film = new Film();
        film.setName("");
        film.setDescription("Track");
        film.setReleaseDate(LocalDate.of(2020, 12, 10));
        film.setDuration(50);

        String expected1 = "Поле не должно быть пустым";

        String actual = validateAndGetFirstMessageTemplate(film);

        Assertions.assertEquals(expected1, actual);
    }

    protected String validateAndGetFirstMessageTemplate(Film obj) {
        return validator.validate(obj).stream()
                .findFirst()
                .orElseThrow()
                .getConstraintDescriptor()
                .getMessageTemplate();
    }
}