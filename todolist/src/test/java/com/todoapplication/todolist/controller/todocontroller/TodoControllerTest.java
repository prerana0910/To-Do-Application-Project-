package com.todoapplication.todolist.controller.todocontroller;

import com.todoapplication.todolist.domain.ToDo;
import com.todoapplication.todolist.domain.User;
import com.todoapplication.todolist.exception.TaskAlreadyExistsException;
import com.todoapplication.todolist.exception.TodoNotFoundException;
import com.todoapplication.todolist.exception.UserNotFoundException;
import com.todoapplication.todolist.repository.ToDoRepository;
import com.todoapplication.todolist.services.todoservices.IToDoService;
import com.todoapplication.todolist.services.todoservices.ToDoServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TodoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IToDoService taskService;

    @InjectMocks
    private TodoController taskController;

    private User user;
    private ToDo newTask;
    private List<ToDo> task;


    @BeforeEach
    void setUp() {
        newTask = new ToDo("Gym Time","To Be Fit", "2024-02-25","10:00 AM","11:00 AM", false,"high", "2024-02-25", false, false,false,false);
        task = new ArrayList<>();
        task.add(newTask);
        user = new User("user1@gmail.com", "user1", "user1@", "987654321",  task);
    }

    @AfterEach
    void tearDown() {
        newTask = null;
        task.remove(newTask);
    }
//
    @Test
    void testAddTaskToUserSuccess() throws UserNotFoundException, TaskAlreadyExistsException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("UserEmailId")).thenReturn("user1@gmail.com");


        ToDo task = new ToDo("Gym Time","To Be Fit", "2024-02-25","10:00 AM","11:00 AM", false,"high", "2024-02-25", false, false,false,false);
        User updatedUser = taskService.saveToDo(task,"user1@gmail.com");
        when(taskService.saveToDo(task,"user1@gmail.com")).thenReturn(user);


        ResponseEntity<?> responseEntity = taskController.addTaskToUser(task, request);


        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());

        verify(taskService, times(2)).saveToDo(task,"user1@gmail.com");
    }

    @Test
    void testAddTaskToUserFailure() throws UserNotFoundException, TaskAlreadyExistsException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("UserEmailId")).thenReturn("user1@gmail.com");

        // Mock TaskService to throw UserNotFoundException
        ToDo task = new ToDo("Gym Time","To Be Fit", "2024-02-25","10:00 AM","11:00 AM", false,"high", "2024-02-25", false, false,false,false);

        when(taskService.saveToDo(task,"user1@gmail.com")).thenThrow(new UserNotFoundException("User not found"));

        // Call the controller method
        ResponseEntity<?> responseEntity = taskController.addTaskToUser(task, request);

        // Verify the response status code
        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateTaskSuccess() throws UserNotFoundException, TaskAlreadyExistsException, TodoNotFoundException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("UserEmailId")).thenReturn("user1@gmail.com");

        // Mock TaskService
        ToDo task =new ToDo("Gym Time","To Be Fit", "2024-02-25","10:00 AM","11:00 AM", false,"high", "2024-02-25", false, false,false,false);
        User updatedUser = taskService.saveToDo(task,"user1@gmail.com");

        when(taskService.updateTodo("user1@gmail.com", task)).thenReturn(user);

        // Call the controller method
        ResponseEntity<?> responseEntity = taskController.updateTask(task, request);

        // Verify the response status code
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        // Verify that the TaskService updateTask method was called
        verify(taskService, times(1)).updateTodo("user1@gmail.com", task);
    }
    @Test
    void testUpdateTaskFailure() throws UserNotFoundException, TodoNotFoundException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("UserEmailId")).thenReturn("user1@gmail.com");

        // Mock TaskService to throw TaskNotExistException
        ToDo task = new ToDo("Gym Time","To Be Fit", "2024-02-25","10:00 AM","11:00 AM", false,"high", "2024-02-25", false, false,false,false);
        when(taskService.updateTodo("user1@gmail.com", task)).thenThrow(new TodoNotFoundException("Task not found"));

        // Call the controller method
        ResponseEntity<?> responseEntity = taskController.updateTask(task, request);

        // Verify the response status code
        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
    }

    @Test
    void testFetchAllTasksSuccess() throws UserNotFoundException, TodoNotFoundException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("UserEmailId")).thenReturn("user1@gmail.com");

        // Mock TaskService
        ToDo task1 = new ToDo("Gym Time","To Be Fit", "2024-02-25","10:00 AM","11:00 AM", false,"high", "2024-02-25", false, false,false,false);
        ToDo task2 = new ToDo("Exercise Time", "To Be Fit", "2024-02-25","10:00 AM","11:00 AM", false,"high", "2024-02-25", false, false,false,false);

        List<ToDo> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        when(taskService.getAllTodo("user1@gmail.com")).thenReturn(tasks);

        // Call the controller method
        ResponseEntity<?> responseEntity = taskController.fetchAllTasks(request);

        // Verify the response status code
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        // Verify the returned tasks
        List<ToDo> returnedTasks = (List<ToDo>) responseEntity.getBody();
        assertEquals(tasks.size(), returnedTasks.size());
        assertEquals(tasks.get(0).getTaskName(), returnedTasks.get(0).getTaskName());
        assertEquals(tasks.get(1).getTaskName(), returnedTasks.get(1).getTaskName());
    }
    @Test
    void testFetchAllTasksFailure() throws UserNotFoundException, TodoNotFoundException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("UserEmailId")).thenReturn("user1@gmail.com");

        // Mock TaskService to throw UserNotFoundException
        when(taskService.getAllTodo("user1@gmail.com")).thenThrow(new UserNotFoundException("User not found"));

        // Call the controller method
        ResponseEntity<?> responseEntity = taskController.fetchAllTasks(request);

        // Verify the response status code
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testFetchAllTasks_Success() throws UserNotFoundException, TodoNotFoundException {
        // Mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("UserEmailId")).thenReturn("user1@gmail.com");

        // Mock ToDoService
        IToDoService toDoService = mock(IToDoService.class);
        when(toDoService.getAllTodo("user1@gmail.com")).thenReturn(new ArrayList<ToDo>());

        // Create TodoController instance
        TodoController todoController = new TodoController(toDoService);

        // Call the controller method
        ResponseEntity<?> responseEntity = todoController.fetchAllTasks(request);

        // Verify the response status code
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testFetchOneTaskFailure() throws UserNotFoundException, TodoNotFoundException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("UserEmailId")).thenReturn("user1@gmail.com");

        // Mock TaskService to throw TaskNotExistException
        when(taskService.getOneTask("user1@gmail.com", "Task Name")).thenThrow(new TodoNotFoundException("Task not found"));

        // Call the controller method
        ResponseEntity<?> responseEntity = taskController.fetchOneTask(request, "Task Name");

        // Verify the response status code
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }




    @Test
    void testDeleteOneTaskSuccess() throws UserNotFoundException, TodoNotFoundException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("UserEmailId")).thenReturn("user1@gmail.com");

        // Mock TaskService to return a user after successfully deleting the task
        when(taskService.deleteTodo(eq("user1@gmail.com"), any(ToDo.class))).thenReturn(true);

        // Call the controller method
        ResponseEntity<?> responseEntity = taskController.deleteOneTask(request, newTask);

        // Verify the response status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteOneTaskFailure() throws UserNotFoundException, TodoNotFoundException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("UserEmailId")).thenReturn("user1@gmail.com");

        // Mock TaskService to throw TodoNotFoundException
        when(taskService.deleteTodo(eq("user1@gmail.com"), any(ToDo.class))).thenThrow(new TodoNotFoundException("Task not found"));

        // Call the controller method with correct argument order
        ResponseEntity<?> responseEntity = taskController.deleteOneTask(request, newTask);

        // Verify the response status code
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }
    @Test
    void testDeleteOneTaskUserNotFound() throws UserNotFoundException, TodoNotFoundException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("UserEmailId")).thenReturn("user1@gmail.com");

        // Mock TaskService to throw UserNotFoundException
        when(taskService.deleteTodo(eq("user1@gmail.com"), any(ToDo.class))).thenThrow(new UserNotFoundException("User not found"));

        // Call the controller method
        ResponseEntity<?> responseEntity = taskController.deleteOneTask(request, newTask);

        // Verify the response status code
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }


}