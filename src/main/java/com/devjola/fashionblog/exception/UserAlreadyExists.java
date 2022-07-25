package com.devjola.fashionblog.exception;

public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists(String message) {
        super(message);
    }
}
