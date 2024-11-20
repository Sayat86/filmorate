package com.example.filmorate.service;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.model.Genre;
import com.example.filmorate.storage.GenreStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> findAll() {
        return genreStorage.findAll();
    }

    public Genre findById(int id) {
        Genre genre = genreStorage.findById(id);
        if (genre == null) {
            throw new NotFoundException("Жанр с таким ID не найден");
        }
        return genre;
    }
}
