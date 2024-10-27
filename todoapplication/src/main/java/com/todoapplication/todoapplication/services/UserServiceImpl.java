package com.todoapplication.todoapplication.services;

import com.todoapplication.todoapplication.domain.User;
import com.todoapplication.todoapplication.exceptions.UserAlreadyExistsException;
import com.todoapplication.todoapplication.exceptions.UserNotFoundException;
import com.todoapplication.todoapplication.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;
    @Autowired
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        Optional<User> optionalUser=userRepository.findById(user.getUserEmail());
        if (optionalUser.isPresent()){
            throw new UserAlreadyExistsException("User already exist");
        }
        else {
            return userRepository.save(user);
        }
    }

    @Override
    public User loginUser(User user) throws UserNotFoundException {
        Optional<User> fetchedUser = userRepository.findByUserEmailAndPassword(user.getUserEmail(),user.getPassword());
        User loggedInUser;
        if(fetchedUser.isEmpty()) {
            throw new UserNotFoundException("User with the given emailId and Password did't Exist");
        }
        else {
            loggedInUser = fetchedUser.get();
        }
        return loggedInUser;
    }
}
