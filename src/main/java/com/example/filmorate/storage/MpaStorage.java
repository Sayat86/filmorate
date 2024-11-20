package com.example.filmorate.storage;

import com.example.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {
    List<Mpa> findAll();
    Mpa findById(int id);
}
