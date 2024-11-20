package com.example.filmorate.service;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.model.Mpa;
import com.example.filmorate.storage.MpaStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MpaService {
    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<Mpa> findAll() {
        return mpaStorage.findAll();
    }

    public Mpa findById(int id) {
        Mpa mpa = mpaStorage.findById(id);
        if (mpa == null) {
            throw new NotFoundException("Mpa с таким ID не найден");
        }
        return mpa;
    }
}
