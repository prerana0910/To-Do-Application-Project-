package com.todoapplication.todolist.controller.todocontroller;

import com.todoapplication.todolist.domain.ToDo;
import com.todoapplication.todolist.exception.TaskAlreadyExistsException;
import com.todoapplication.todolist.exception.TodoNotFoundException;
import com.todoapplication.todolist.exception.UserNotFoundException;
import com.todoapplication.todolist.services.todoservices.IToDoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v3")
public class TodoController {
    private final IToDoService toDoService;

    @Autowired
    public TodoController(IToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/getUserDetails")
    public ResponseEntity<?> getUserDetails(HttpServletRequest httpServletRequest) {
        ResponseEntity<?> responseEntity;
        try {
            String emailId = httpServletRequest.getAttribute("UserEmailId").toString();
            responseEntity = new ResponseEntity<>(toDoService.getUser(emailId),HttpStatus.PARTIAL_CONTENT);
        }
        catch(UserNotFoundException exception) {
            responseEntity = new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
        }
        return responseEntity;

    }


    @PostMapping("/addToDoTask")
    public ResponseEntity<?> addTaskToUser(@RequestBody ToDo toDo, HttpServletRequest httpServletRequest){
        ResponseEntity<?> responseEntity;
        try {
            String userEmailId = httpServletRequest.getAttribute("UserEmailId").toString();
            responseEntity = new ResponseEntity<>(toDoService.saveToDo(toDo,userEmailId),HttpStatus.ACCEPTED);
        }
        catch(UserNotFoundException | TaskAlreadyExistsException exception) {
            responseEntity = new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }

    @PutMapping("/updateToDoTask")
    public ResponseEntity<?> updateTask(@RequestBody ToDo toDo, HttpServletRequest httpServletRequest){
        ResponseEntity<?> responseEntity;
        try {
            String userEmailId = httpServletRequest.getAttribute("UserEmailId").toString();
            responseEntity = new ResponseEntity<>(toDoService.updateTodo(userEmailId,toDo),HttpStatus.ACCEPTED);
        }
        catch(UserNotFoundException | TodoNotFoundException exception) {
            responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }

    @GetMapping("/getAllToDoTask")
    public ResponseEntity<?> fetchAllTasks(HttpServletRequest httpServletRequest){
        ResponseEntity<?> responseEntity;
        try {
            String userEmailId = httpServletRequest.getAttribute("UserEmailId").toString();
            responseEntity = new ResponseEntity<>(toDoService.getAllTodo(userEmailId),HttpStatus.OK);
        }
        catch(UserNotFoundException | TodoNotFoundException exception) {
            responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping("/getOneToDoTask/{tasktitle}")
    public ResponseEntity<?> fetchOneTask(HttpServletRequest httpServletRequest, @PathVariable String tasktitle){
        ResponseEntity<?> responseEntity;
        try {
            String userEmailId = httpServletRequest.getAttribute("UserEmailId").toString();
            responseEntity = new ResponseEntity<>(toDoService.getOneTask(userEmailId, tasktitle), HttpStatus.OK);
        }
        catch(UserNotFoundException | TodoNotFoundException exception) {
            responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
    @DeleteMapping("/deleteToDoTask")
    public ResponseEntity<?> deleteOneTask(HttpServletRequest httpServletRequest, @RequestBody ToDo task){
        ResponseEntity<?> responseEntity;
        try {
            String userEmailId = httpServletRequest.getAttribute("UserEmailId").toString();
            responseEntity = new ResponseEntity<>(toDoService.deleteTodo(userEmailId, task), HttpStatus.OK);
        }
        catch(UserNotFoundException | TodoNotFoundException exception) {
            responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
}
