package com.appcent.todoapp.exception;

import org.springframework.http.HttpStatus;

public class TodoListNotFoundException extends ApiException {
    public TodoListNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
