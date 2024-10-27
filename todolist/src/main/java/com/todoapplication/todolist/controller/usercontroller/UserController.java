package com.todoapplication.todolist.controller.usercontroller;

import com.todoapplication.todolist.domain.User;
import com.todoapplication.todolist.exception.UserAlreadyExistsException;
import com.todoapplication.todolist.services.userservices.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        ResponseEntity<?> responseEntity;
        try{
            responseEntity = new ResponseEntity<>(userService.registerUser(user), HttpStatus.ACCEPTED);
        }catch (UserAlreadyExistsException exception){
            responseEntity =  new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }
}
