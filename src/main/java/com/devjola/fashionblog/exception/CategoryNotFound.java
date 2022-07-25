package com.devjola.fashionblog.exception;

public class CategoryNotFound extends RuntimeException{
    public CategoryNotFound(String message) {
        super(message);
    }
}
