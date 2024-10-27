package com.todoapplication.todolist.services.userservices;

import com.todoapplication.todolist.domain.User;
import com.todoapplication.todolist.exception.UserAlreadyExistsException;

public interface IUserService {
    User registerUser(User user) throws UserAlreadyExistsException;
}
