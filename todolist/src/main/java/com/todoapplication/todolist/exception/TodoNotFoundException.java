package com.todoapplication.todolist.exception;

public class TodoNotFoundException extends Throwable {
    public TodoNotFoundException(String message) {
        super(message);
    }
}
