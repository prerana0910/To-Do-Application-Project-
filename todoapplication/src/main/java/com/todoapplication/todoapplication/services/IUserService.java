package com.todoapplication.todoapplication.services;

import com.todoapplication.todoapplication.domain.User;
import com.todoapplication.todoapplication.exceptions.UserAlreadyExistsException;
import com.todoapplication.todoapplication.exceptions.UserNotFoundException;
import com.todoapplication.todoapplication.repository.IUserRepository;

public interface IUserService  {
    User registerUser(User user) throws UserAlreadyExistsException;
    User loginUser(User user) throws UserNotFoundException;

}
