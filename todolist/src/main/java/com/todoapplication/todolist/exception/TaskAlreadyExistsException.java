package com.todoapplication.todolist.exception;

public class TaskAlreadyExistsException extends Throwable {
    public TaskAlreadyExistsException(String message) {
        super(message);
    }
}
