package com.appcent.todoapp.exception;

import org.springframework.http.HttpStatus;

public class TodoItemNotFoundException extends ApiException {
    public TodoItemNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
