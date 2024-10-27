package com.todoapplication.todolist.exception;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String userNotFound) {
        super(userNotFound);
    }
}
