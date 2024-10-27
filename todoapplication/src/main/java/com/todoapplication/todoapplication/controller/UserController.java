package com.todoapplication.todoapplication.controller;

import com.todoapplication.todoapplication.domain.User;
import com.todoapplication.todoapplication.exceptions.UserAlreadyExistsException;
import com.todoapplication.todoapplication.exceptions.UserNotFoundException;
import com.todoapplication.todoapplication.security.ISecurityTokenGenrerator;
import com.todoapplication.todoapplication.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2")
public class UserController {
    private final IUserService userService;
    private final ISecurityTokenGenrerator securityTokenGenerator;
    @Autowired
    public UserController(IUserService userService, ISecurityTokenGenrerator securityTokenGenerator) {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }
    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        ResponseEntity<?> responseEntity;
        try{
            responseEntity = new ResponseEntity<>(userService.registerUser(user),HttpStatus.ACCEPTED);
        }catch (UserAlreadyExistsException exception){
            responseEntity =  new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        ResponseEntity<?> responseEntity;
        try {
            User fetchedUser = userService.loginUser(user);
            Map<String, String> map = securityTokenGenerator.generateToken(fetchedUser);
            responseEntity = new ResponseEntity<>(map,HttpStatus.ACCEPTED);
        }
        catch(UserNotFoundException exception) {
            responseEntity = new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }
}
