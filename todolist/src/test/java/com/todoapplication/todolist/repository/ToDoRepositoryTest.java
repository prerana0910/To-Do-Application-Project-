package com.todoapplication.todolist.repository;

import com.todoapplication.todolist.domain.ToDo;
import com.todoapplication.todolist.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest

class ToDoRepositoryTest {
    @Autowired
    private ToDoRepository toDoRepository;
    @BeforeEach
    public void setup() {
        toDoRepository = mock(ToDoRepository.class);
    }

    @Test
    public void testFindByIdSuccess() {
        String userEmail = "test@example.com";
        User user = new User();
        user.setUserEmail(userEmail);

        when(toDoRepository.findById(userEmail)).thenReturn(Optional.of(user));

        Optional<User> optionalUser = toDoRepository.findById(userEmail);

        verify(toDoRepository, times(1)).findById(userEmail);
        assertTrue(optionalUser.isPresent());
        assertEquals(user, optionalUser.get());
    }

    @Test
    public void testFindByIdFailer() {
        String userEmail = "nonExistingUser@example.com";

        when(toDoRepository.findById(userEmail)).thenReturn(Optional.empty());

        Optional<User> optionalUser = toDoRepository.findById(userEmail);

        verify(toDoRepository, times(1)).findById(userEmail);
        assertFalse(optionalUser.isPresent());
    }

    @Test
    public void testSaveSuccess() {
        User user = new User();
        user.setUserEmail("test@example.com");

        when(toDoRepository.save(user)).thenReturn(user);

        User savedUser = toDoRepository.save(user);

        verify(toDoRepository, times(1)).save(user);
        assertNotNull(savedUser);
        assertEquals(user, savedUser);
    }

    @Test
    public void testDeleteSuccess() {
        User user = new User();
        user.setUserEmail("test@example.com");

        toDoRepository.delete(user);

        verify(toDoRepository, times(1)).delete(user);
    }
    @Test
    public void testDeleteFailer() {
        User user = new User();
        user.setUserEmail("nonExistingUser@example.com");

        doThrow(IllegalArgumentException.class).when(toDoRepository).delete(user);

        assertThrows(IllegalArgumentException.class, () -> toDoRepository.delete(user));

        verify(toDoRepository, times(1)).delete(user);
    }

    @Test
    public void testFindAllSuccess() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("priya@gmail.com","priya","priya1","2222222",null));
        userList.add(new User("triya@gmail.com","triya","triya1","2222222",null));

        when(toDoRepository.findAll()).thenReturn(userList);

        List<User> fetchedUsers = toDoRepository.findAll();

        verify(toDoRepository, times(1)).findAll();
        assertEquals(userList.size(), fetchedUsers.size());
        assertEquals(userList, fetchedUsers);
    }
    @Test
    public void testFindByIdFailed() {
        // Suppose we expect to find a user with this email
        String userEmail = "existingUser@example.com";

        // But the repository returns an empty optional
        when(toDoRepository.findById(userEmail)).thenReturn(Optional.empty());

        // Attempt to fetch the user
        Optional<User> optionalUser = toDoRepository.findById(userEmail);

        // Verification and assertion
        verify(toDoRepository, times(1)).findById(userEmail);
        assertFalse(optionalUser.isPresent()); // This assertion should fail because the user is not found
    }

}