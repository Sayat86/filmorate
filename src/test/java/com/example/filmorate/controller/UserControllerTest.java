package com.example.filmorate.controller;


import com.example.filmorate.model.Film;
import com.example.filmorate.model.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Test
    void correctEmailTest() {
        User user = new User();
        user.setEmail("");
        user.setLogin("Kira");
        user.setName("Mo");
        user.setBirthday(LocalDate.of(2020, 10, 10));
        String expected = "Invalid email format.";

        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void correctLoginTest() {
        User user = new User();
        user.setEmail("s.beysembayev@mail.ru");
        user.setLogin("");
        user.setName("Mo");
        user.setBirthday(LocalDate.of(2010, 10, 10));
        String expected = "Login cannot be null.";

        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    protected String validateAndGetFirstMessageTemplate(User obj) {
        return validator.validate(obj).stream()
                .findFirst()
                .orElseThrow()
                .getConstraintDescriptor()
                .getMessageTemplate();
    }
}