package com.todoapplication.todolist.services.userservices;

import com.todoapplication.todolist.domain.User;
import com.todoapplication.todolist.exception.UserAlreadyExistsException;
import com.todoapplication.todolist.proxy.IUserProxy;
import com.todoapplication.todolist.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private final ToDoRepository toDoRepository;
    private final IUserProxy userProxy;

    @Autowired
    public UserServiceImpl(ToDoRepository toDoRepository, IUserProxy userProxy) {
        this.toDoRepository = toDoRepository;
        this.userProxy = userProxy;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        Optional<User> optionalUser=toDoRepository.findById(user.getUserEmail());
        User addedUser;
        if (optionalUser.isPresent()){
            throw new UserAlreadyExistsException("User already exist");
        }
        else {
            addedUser = toDoRepository.save(user);
            userProxy.saveUser(addedUser);

        }
        return  addedUser;
    }
}
