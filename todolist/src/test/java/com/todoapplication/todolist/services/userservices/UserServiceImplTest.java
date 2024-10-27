package com.todoapplication.todolist.services.userservices;

import com.todoapplication.todolist.domain.User;
import com.todoapplication.todolist.exception.UserAlreadyExistsException;
import com.todoapplication.todolist.repository.ToDoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private ToDoRepository toDoRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    User firstUser,secondUser;

    @BeforeEach
    void setUp() {
        firstUser = new User("priya@gmail.com","priya","priya1","2222222",null);
        secondUser=new User("sanj@gmail.com","sanji","sanji1","2232252365",null);
    }

    @AfterEach
    void tearDown() {
        firstUser=null;
        secondUser=null;
    }

    @Test
    public void registerUserSuccess() throws UserAlreadyExistsException {
        when(toDoRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        when(toDoRepository.save(any())).thenReturn(firstUser);
        assertEquals(firstUser, userServiceImpl.registerUser(firstUser));
        verify(toDoRepository,times(1)).findById(any());
        verify(toDoRepository,times(1)).save(any());

    }
    @Test
    public void givenUserToSaveReturnSavedUserFailure() throws UserAlreadyExistsException {
        when(toDoRepository.findById(anyString())).thenReturn(Optional.ofNullable(firstUser));
        assertThrows(UserAlreadyExistsException.class,()-> userServiceImpl.registerUser(firstUser));
        verify(toDoRepository,times(1)).findById(any());
        verify(toDoRepository,times(0)).save(any());
    }
    }