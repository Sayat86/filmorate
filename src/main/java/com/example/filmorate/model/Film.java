package com.example.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
@Data
public class Film {
    private int id;
    @NotBlank(message = "Поле не должно быть пустым")
    private String name;
    @Size(max = 200)
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "Must be greater than 0")
    private int duration;


}
