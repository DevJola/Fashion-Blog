package com.devjola.fashionblog.exception;

public class CategoryAlreadyExists extends RuntimeException {
    public CategoryAlreadyExists(String message) {
        super(message);
    }
}
