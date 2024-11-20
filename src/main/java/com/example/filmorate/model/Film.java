package com.example.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "Поле не должно быть пустым")
    private String name;
    @Size(max = 200, message = "Длина не может превышать 200 символов")
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "Must be greater than 0")
    private int duration;
    private Mpa mpa;

    private final Set<Genre> genres = new LinkedHashSet<>();

}
