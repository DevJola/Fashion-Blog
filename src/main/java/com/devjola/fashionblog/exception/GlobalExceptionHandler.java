package com.devjola.fashionblog.exception;

import com.devjola.fashionblog.exception.*;
import com.devjola.fashionblog.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {
    private final ErrorResponse errorResponse;

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(final UserAlreadyExists ex) {
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("Check to make sure that user does not already exist");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(final UserNotFound ex) {
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("Check that user has signed up");
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(NotPermittedToPerformThisOperation.class)
    public ResponseEntity<ErrorResponse> handleNotPermittedToPerformThisOperationException(
            final NotPermittedToPerformThisOperation ex) {
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("check if user who wants to upload a post is an admin");
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(CategoryAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyExistsException(
            final CategoryAlreadyExists ex) {
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("check if the category already exists");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(ListIsEmpty.class)
    public ResponseEntity<ErrorResponse> handleListIsEmptyException(
            final ListIsEmpty ex) {
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("There might not be any uploaded post");
        errorResponse.setStatus(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(PostNotFound.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(
            final PostNotFound ex) {
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("Post with specified id might not exist");
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(CategoryNotFound.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(
            final CategoryNotFound ex) {
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("Category with specified id might not exist");
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

}
