package com.todoapplication.todolist.services.todoservices;

import com.todoapplication.todolist.domain.ToDo;
import com.todoapplication.todolist.domain.User;
import com.todoapplication.todolist.exception.TaskAlreadyExistsException;
import com.todoapplication.todolist.exception.TodoNotFoundException;
import com.todoapplication.todolist.exception.UserNotFoundException;

import java.util.List;

public interface IToDoService {
    public User getUser(String emailId) throws UserNotFoundException;
    User saveToDo(ToDo toDo,String userEmail) throws UserNotFoundException, TaskAlreadyExistsException;

    List<ToDo> getAllTodo(String userEmail) throws UserNotFoundException,TodoNotFoundException;
    boolean deleteTodo(String userEmail, ToDo task) throws TodoNotFoundException, UserNotFoundException;
    User updateTodo(String userEmail,ToDo toDo) throws UserNotFoundException, TodoNotFoundException;
    List<ToDo> getOneTask(String emailId, String taskName) throws UserNotFoundException, TodoNotFoundException;
}
