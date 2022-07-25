package com.devjola.fashionblog.exception;

public class NotPermittedToPerformThisOperation extends RuntimeException{
    public NotPermittedToPerformThisOperation(String message) {
        super(message);
    }
}
