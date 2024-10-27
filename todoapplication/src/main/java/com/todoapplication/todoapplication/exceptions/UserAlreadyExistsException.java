package com.todoapplication.todoapplication.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String userAlreadyExist) {
        super(userAlreadyExist);
    }
}
