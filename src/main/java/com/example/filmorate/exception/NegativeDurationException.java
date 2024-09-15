package com.example.filmorate.exception;

public class NegativeDurationException extends RuntimeException{
    public NegativeDurationException(String message) {
        super(message);}
}
