package com.todoapplication.todolist.services.todoservices;

import com.todoapplication.todolist.domain.ToDo;
import com.todoapplication.todolist.domain.User;
import com.todoapplication.todolist.exception.TaskAlreadyExistsException;
import com.todoapplication.todolist.exception.TodoNotFoundException;
import com.todoapplication.todolist.exception.UserNotFoundException;
import com.todoapplication.todolist.repository.ToDoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {
    @Mock
    private ToDoRepository toDoRepository;
    @InjectMocks
    private ToDoServiceImpl toDoService;
    @BeforeEach
    public void setup() {
        toDoRepository = mock(ToDoRepository.class);
        toDoService = new ToDoServiceImpl(toDoRepository);
    }

//    @Test
//    public void testSaveToDo_Success() throws UserNotFoundException, TaskAlreadyExistsException {
//        User user = new User("priya@gmail.com","priya","priya1","2222222",null);
//        ToDo toDo = new ToDo();
//        toDo.setTasktitle("Test Task");
//
//        when(toDoRepository.findById(user.getUserEmail())).thenReturn(Optional.of(user));
//        when(toDoRepository.save(user)).thenReturn(user);
//
//        User savedUser = toDoService.saveToDo(toDo, user.getUserEmail());
//
//        verify(toDoRepository, times(1)).findById(user.getUserEmail());
//        verify(toDoRepository, times(1)).save(user);
//        assert savedUser.equals(user);
//    }
//
//    // Add more test methods for other scenarios
//
//    @Test
//    public void testSaveToDo_UserNotFound() {
//        ToDo toDo = new ToDo();
//        toDo.setTasktitle("Test Task");
//
//        assertThrows(UserNotFoundException.class, () -> toDoService.saveToDo(toDo, "nonExistingUser@example.com"));
//
//        verify(toDoRepository, times(1)).findById("nonExistingUser@example.com");
//        verify(toDoRepository, never()).save(any());
//    }
//
//    @Test
//    public void testSaveToDo_TaskAlreadyExists() {
//        User user = new User("priya@gmail.com","priya","priya1","2222222",null);
//        ToDo existingToDo = new ToDo();
//        existingToDo.setTasktitle("Test Task");
//        List<ToDo> existingTasks = new ArrayList<>();
//        existingTasks.add(existingToDo);
//        user.setToDo(existingTasks);
//
//        ToDo newToDo = new ToDo();
//        newToDo.setTasktitle("Test Task");
//
//        when(toDoRepository.findById(user.getUserEmail())).thenReturn(Optional.of(user));
//
//        assertThrows(TaskAlreadyExistsException.class, () -> toDoService.saveToDo(newToDo, user.getUserEmail()));
//
//        verify(toDoRepository, times(1)).findById(user.getUserEmail());
//        verify(toDoRepository, never()).save(any());
//    }
//
//
//    @Test
//    public void testGetAllTodo_Success() throws UserNotFoundException, TodoNotFoundException, TodoNotFoundException {
//        User user = new User("priya@gmail.com","priya","priya1","2222222",null);
//        ToDo todo1 = new ToDo();
//        todo1.setTasktitle("Task 1");
//        ToDo todo2 = new ToDo();
//        todo2.setTasktitle("Task 2");
//        List<ToDo> todos = new ArrayList<>();
//        todos.add(todo1);
//        todos.add(todo2);
//        user.setToDo(todos);
//
//        when(toDoRepository.findById(user.getUserEmail())).thenReturn(Optional.of(user));
//
//        List<ToDo> fetchedTodos = toDoService.getAllTodo(user.getUserEmail());
//
//        verify(toDoRepository, times(1)).findById(user.getUserEmail());
//        assertIterableEquals(todos, fetchedTodos);
//    }
//
//    @Test
//    public void testGetAllTodo_UserNotFound() {
//        assertThrows(UserNotFoundException.class, () -> toDoService.getAllTodo("nonExistingUser@example.com"));
//
//        verify(toDoRepository, times(1)).findById("nonExistingUser@example.com");
//    }
//    @Test
//    public void testDeleteTodo_Success() throws TodoNotFoundException, UserNotFoundException {
//        User user = new User("priya@gmail.com","priya","priya1","2222222",null);
//        ToDo todo1 = new ToDo();
//        todo1.setTaskId(1);
//        todo1.setTasktitle("Task 1");
//        ToDo todo2 = new ToDo();
//        todo2.setTaskId(2);
//        todo2.setTasktitle("Task 2");
//        List<ToDo> todos = new ArrayList<>();
//        todos.add(todo1);
//        todos.add(todo2);
//        user.setToDo(todos);
//
//        when(toDoRepository.findById(user.getUserEmail())).thenReturn(Optional.of(user));
//        when(toDoRepository.save(user)).thenReturn(user);
//
//        User updatedUser = toDoService.deleteTodo(user.getUserEmail(), 1);
//
//        verify(toDoRepository, times(1)).findById(user.getUserEmail());
//        verify(toDoRepository, times(1)).save(user);
//        assertTrue(updatedUser.getToDo().size() == 1);
//        assertFalse(updatedUser.getToDo().contains(todo1));
//    }
//
//    @Test
//    public void testDeleteTodo_TodoNotFound() {
//        User user = new User("priya@gmail.com","priya","priya1","2222222",null);
//        ToDo todo1 = new ToDo();
//        todo1.setTaskId(1);
//        todo1.setTasktitle("Task 1");
//        List<ToDo> todos = new ArrayList<>();
//        todos.add(todo1);
//        user.setToDo(todos);
//
//        when(toDoRepository.findById(user.getUserEmail())).thenReturn(Optional.of(user));
//
//        assertThrows(TodoNotFoundException.class, () -> toDoService.deleteTodo(user.getUserEmail(), 2));
//
//        verify(toDoRepository, times(1)).findById(user.getUserEmail());
//        verify(toDoRepository, never()).save(any());
//    }
//
//    @Test
//    public void testDeleteTodo_UserNotFound() {
//        assertThrows(UserNotFoundException.class, () -> toDoService.deleteTodo("nonExistingUser@example.com", 1));
//
//        verify(toDoRepository, times(1)).findById("nonExistingUser@example.com");
//        verify(toDoRepository, never()).save(any());
//    }
//
//
//    @Test
//    public void testUpdateTodo_Success() throws UserNotFoundException, TodoNotFoundException {
//        // Create a user with some existing ToDo tasks
//        User user = new User("priya@gmail.com","priya","priya1","2222222",null);
//        ToDo existingToDo = new ToDo();
//        existingToDo.setTaskId(1);
//        existingToDo.setTasktitle("Existing Task");
//        List<ToDo> todos = new ArrayList<>();
//        todos.add(existingToDo);
//        user.setToDo(todos);
//
//        // Create an updated ToDo task
//        ToDo updatedToDo = new ToDo();
//        updatedToDo.setTasktitle("Existing Task"); // Set task title that matches existing task
//        updatedToDo.setDescription("Updated Description");
//
//        // Mock the repository behavior
//        when(toDoRepository.findById(user.getUserEmail())).thenReturn(Optional.of(user));
//        when(toDoRepository.save(user)).thenReturn(user);
//
//        // Call the method under test
//        User updatedUser = toDoService.updateTodo(user.getUserEmail(), updatedToDo);
//
//        // Verify repository method calls
//        verify(toDoRepository, times(1)).findById(user.getUserEmail());
//        verify(toDoRepository, times(1)).save(user);
//
//        // Verify the updated user contains the updated ToDo task
//        assertTrue(updatedUser.getToDo().stream().anyMatch(t -> t.getTasktitle().equals(updatedToDo.getTasktitle())));
//        assertTrue(updatedUser.getToDo().stream().anyMatch(t -> t.getDescription().equals(updatedToDo.getDescription())));
//    }
//
//
//    @Test
//    public void testUpdateTodo_UserNotFound() {
//        ToDo updatedToDo = new ToDo();
//        updatedToDo.setTasktitle("Updated Task");
//
//        assertThrows(UserNotFoundException.class, () -> toDoService.updateTodo("nonExistingUser@example.com", updatedToDo));
//
//        verify(toDoRepository, times(1)).findById("nonExistingUser@example.com");
//        verify(toDoRepository, never()).save(any());
//    }
//
//    @Test
//    public void testUpdateTodo_TodoNotFound() {
//        User user = new User("priya@gmail.com","priya","priya1","2222222",null);
//        ToDo updatedToDo = new ToDo();
//        updatedToDo.setTasktitle("Updated Task");
//
//        when(toDoRepository.findById(user.getUserEmail())).thenReturn(Optional.of(user));
//
//        assertThrows(TodoNotFoundException.class, () -> toDoService.updateTodo(user.getUserEmail(), updatedToDo));
//
//        verify(toDoRepository, times(1)).findById(user.getUserEmail());
//        verify(toDoRepository, never()).save(any());
//    }
//
//
//    @Test
//    public void testGetOneTask_Success() throws UserNotFoundException, TodoNotFoundException {
//        User user = new User("priya@gmail.com","priya","priya1","2222222",null);
//        ToDo todo1 = new ToDo();
//        todo1.setTasktitle("Task 1");
//        ToDo todo2 = new ToDo();
//        todo2.setTasktitle("Task 2");
//        List<ToDo> todos = new ArrayList<>();
//        todos.add(todo1);
//        todos.add(todo2);
//        user.setToDo(todos);
//
//        String taskName = "Task 1";
//
//        when(toDoRepository.findById(user.getUserEmail())).thenReturn(Optional.of(user));
//
//        ToDo fetchedTodo = toDoService.getOneTask(user.getUserEmail(), taskName);
//
//        verify(toDoRepository, times(1)).findById(user.getUserEmail());
//        assertEquals(todo1, fetchedTodo);
//    }
//
//    @Test
//    public void testGetOneTask_UserNotFound() {
//        String taskName = "Task 1";
//
//        assertThrows(UserNotFoundException.class, () -> toDoService.getOneTask("nonExistingUser@example.com", taskName));
//
//        verify(toDoRepository, times(1)).findById("nonExistingUser@example.com");
//    }
//
//    @Test
//    public void testGetOneTask_TodoNotFound() throws UserNotFoundException {
//        User user = new User("priya@gmail.com","priya","priya1","2222222",null);
//        ToDo todo1 = new ToDo();
//        todo1.setTasktitle("Task 1");
//        List<ToDo> todos = new ArrayList<>();
//        todos.add(todo1);
//        user.setToDo(todos);
//
//        String taskName = "Non-existing Task";
//
//        when(toDoRepository.findById(user.getUserEmail())).thenReturn(Optional.of(user));
//
//        assertThrows(TodoNotFoundException.class, () -> toDoService.getOneTask(user.getUserEmail(), taskName));
//
//        verify(toDoRepository, times(1)).findById(user.getUserEmail());
//    }
@Test
public void testSaveToDo_Success() {
    // Create a mock ToDo object
    ToDo toDo = new ToDo();
    toDo.setTaskName("Example Task");
    toDo.setTaskDate("2024-02-25");
    toDo.setTaskStartTime("08:00:00");
    toDo.setTaskDueTime("09:00:00");
    toDo.setIsrepetitive(false);

    // Mock existing user in the repository
    User existingUser = new User();
    existingUser.setUserEmail("test@example.com");
    existingUser.setToDo(new ArrayList<>());

    // Mock repository behavior
    Mockito.when(toDoRepository.findById("test@example.com")).thenReturn(Optional.of(existingUser));
    Mockito.when(toDoRepository.save(existingUser)).thenReturn(existingUser);

    // Invoke the method
    try {
        User savedUser = toDoService.saveToDo(toDo, "test@example.com");
        // Add assertions here to validate the behavior
    } catch (Exception e) {
        fail("Exception occurred: " + e.getMessage());
    } catch (UserNotFoundException e) {
        throw new RuntimeException(e);
    } catch (TaskAlreadyExistsException e) {
        throw new RuntimeException(e);
    }
}
    @Test
    public void testSaveToDo_Failure_TaskAlreadyExists() {
        // Create a mock ToDo object
        ToDo toDo = new ToDo();
        toDo.setTaskName("Example Task");
        toDo.setTaskDate("2024-02-25");
        toDo.setTaskStartTime("08:00:00");
        toDo.setTaskDueTime("09:00:00");
        toDo.setIsrepetitive(false);

        // Mock existing user in the repository with the same task name
        User existingUser = new User();
        existingUser.setUserEmail("test@example.com");
        existingUser.setToDo(Collections.singletonList(toDo));

        // Mock repository behavior
        Mockito.when(toDoRepository.findById("test@example.com")).thenReturn(Optional.of(existingUser));

        // Invoke the method and expect an exception
        assertThrows(TaskAlreadyExistsException.class, () -> {
            toDoService.saveToDo(toDo, "test@example.com");
        });
    }
    @Test
    public void testGetAllTodo_Success() {
        // Create a mock user with existing tasks
        User user = new User();
        user.setUserEmail("test@example.com");
        ToDo task1 = new ToDo();
        task1.setTaskName("Task 1");
        ToDo task2 = new ToDo();
        task2.setTaskName("Task 2");
        user.setToDo(Arrays.asList(task1, task2));

        // Mock repository behavior
        Mockito.when(toDoRepository.findById("test@example.com")).thenReturn(Optional.of(user));

        // Invoke the method
        try {
            List<ToDo> todoList = toDoService.getAllTodo("test@example.com");
            // Add assertions here to validate the behavior
            assertEquals(2, todoList.size()); // Expecting 2 tasks
            assertTrue(todoList.contains(task1)); // Expecting task1 to be present
            assertTrue(todoList.contains(task2)); // Expecting task2 to be present
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TodoNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testGetAllTodo_Failure_NoTasks() {
        // Create a mock user without any tasks
        User user = new User();
        user.setUserEmail("test@example.com");
        user.setToDo(null);

        // Mock repository behavior
        Mockito.when(toDoRepository.findById("test@example.com")).thenReturn(Optional.of(user));

        // Invoke the method and expect TodoNotFoundException
        assertThrows(TodoNotFoundException.class, () -> {
            toDoService.getAllTodo("test@example.com");
        });
    }
    @Test
    public void testDeleteTodo_Success()  {
        // Create a mock user with existing tasks
        User user = new User();
        user.setUserEmail("test@example.com");
        ToDo task1 = new ToDo();
        task1.setTaskName("Task 1");
        task1.setIsrepetitive(false);
        task1.setIsapplyToRepetitive(false);
        ToDo task2 = new ToDo();
        task2.setTaskName("Task 2");
        task2.setIsrepetitive(true);
        task2.setIsapplyToRepetitive(true);
        user.setToDo(new ArrayList<>(Arrays.asList(task1, task2))); // Use ArrayList constructor

        // Mock repository behavior
        Mockito.when(toDoRepository.findById("test@example.com")).thenReturn(Optional.of(user));

        // Invoke the method
        boolean deleted;
        try {
            deleted = toDoService.deleteTodo("test@example.com", task1);
        } catch (UserNotFoundException | TodoNotFoundException e) {
            // Exception should not occur in this test case
            fail("Unexpected exception occurred: " + e.getMessage());
            return;
        }

        // Add assertions here to validate the behavior
        assertTrue(deleted); // Expecting task1 to be deleted
        assertEquals(1, user.getToDo().size()); // Expecting 1 task remaining
        assertFalse(user.getToDo().contains(task1)); // Expecting task1 to be removed
    }
    @Test
    public void testDeleteTodo_Failure_TaskNotFound() {
        // Create a mock user with existing tasks
        User user = new User();
        user.setUserEmail("test@example.com");
        ToDo task1 = new ToDo();
        task1.setTaskName("Task 1");
        user.setToDo(Collections.singletonList(task1));

        // Mock repository behavior
        Mockito.when(toDoRepository.findById("test@example.com")).thenReturn(Optional.of(user));

        // Create a task to delete that doesn't exist
        ToDo taskToDelete = new ToDo();
        taskToDelete.setTaskName("Non-existent Task");

        // Invoke the method and expect TodoNotFoundException
        assertThrows(TodoNotFoundException.class, () -> {
            toDoService.deleteTodo("test@example.com", taskToDelete);
        });
    }
    @Test
    public void testUpdateTodo_Success() {
        // Create a mock user with an existing task
        User user = new User();
        user.setUserEmail("test@example.com");
        ToDo existingTask = new ToDo();
        existingTask.setTaskName("Existing Task");
        existingTask.setTaskDescription("Old Description");
        existingTask.setTaskDate("2024-02-25");
        existingTask.setTaskStartTime("08:00:00");
        existingTask.setTaskDueTime("09:00:00");
        existingTask.setIsrepetitive(false);
        existingTask.setIscompleted(false);
        existingTask.setIsarchived(false);
        user.setToDo(Collections.singletonList(existingTask));

        // Mock repository behavior
        Mockito.when(toDoRepository.findById("test@example.com")).thenReturn(Optional.of(user));

        // Create a modified task
        ToDo updatedTask = new ToDo();
        updatedTask.setTaskName("Existing Task");
        updatedTask.setTaskDescription("New Description");
        updatedTask.setTaskDate("2024-02-25");
        updatedTask.setTaskStartTime("09:00:00");
        updatedTask.setTaskDueTime("10:00:00");
        updatedTask.setIsrepetitive(false);
        updatedTask.setIscompleted(true);
        updatedTask.setIsarchived(true);

        // Invoke the method
        try {
            User updatedUser = toDoService.updateTodo("test@example.com", updatedTask);
            // Add assertions here to validate the behavior
            assertEquals("New Description", updatedUser.getToDo().get(0).getTaskDescription());
            assertEquals("09:00:00", updatedUser.getToDo().get(0).getTaskStartTime());
            assertTrue(updatedUser.getToDo().get(0).getIscompleted());
            assertTrue(updatedUser.getToDo().get(0).getIsarchived());
        } catch (Exception | UserNotFoundException | TodoNotFoundException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
    @Test
    public void testGetOneTask_Success() {
        // Create a mock user with existing tasks
        User user = new User();
        user.setUserEmail("test@example.com");
        ToDo task1 = new ToDo();
        task1.setTaskName("Task 1");
        ToDo task2 = new ToDo();
        task2.setTaskName("Task 2");
        user.setToDo(Arrays.asList(task1, task2));

        // Mock repository behavior
        Mockito.when(toDoRepository.findById("test@example.com")).thenReturn(Optional.of(user));

        // Invoke the method
        try {
            List<ToDo> foundTasks = toDoService.getOneTask("test@example.com", "Task 1");
            // Add assertions here to validate the behavior
            assertEquals(1, foundTasks.size()); // Expecting 1 task
            assertTrue(foundTasks.contains(task1)); // Expecting task1 to be found
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TodoNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testGetOneTask_Failure_NoTasks() {
        // Create a mock user without any tasks
        User user = new User();
        user.setUserEmail("test@example.com");
        user.setToDo(null);

        // Mock repository behavior
        Mockito.when(toDoRepository.findById("test@example.com")).thenReturn(Optional.of(user));

        // Invoke the method and expect TodoNotFoundException
        assertThrows(TodoNotFoundException.class, () -> {
            toDoService.getOneTask("test@example.com", "Non-existent Task");
        });
    }

}