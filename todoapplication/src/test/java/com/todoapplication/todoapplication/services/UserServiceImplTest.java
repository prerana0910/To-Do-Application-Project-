package com.todoapplication.todoapplication.services;

import com.todoapplication.todoapplication.domain.User;
import com.todoapplication.todoapplication.exceptions.UserAlreadyExistsException;
import com.todoapplication.todoapplication.exceptions.UserNotFoundException;
import com.todoapplication.todoapplication.repository.IUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;
    private User user1,user2;


    @BeforeEach
    void setUp() {
        user1 = new User("user1@gmail.com","user1");
        user2 = new User("user2@gmail.com","user2");
    }

    @AfterEach
    void tearDown() {
        user1 = null;
        user2 = null;
    }

    @Test
    void testRegisterUserSuccess() throws UserAlreadyExistsException {
        when(userRepository.findById("user1@gmail.com")).thenReturn(Optional.empty());
        when(userRepository.save(user1)).thenReturn(user1);

        User registeredUser = userServiceImpl.registerUser(user1);

        assertEquals(user1, registeredUser);
    }
    @Test
    void testRegisterUserFailure() {
        when(userRepository.findById("user1@gmail.com")).thenReturn(Optional.of(user1));

        assertThrows(UserAlreadyExistsException.class, () -> {
            userServiceImpl.registerUser(user1);
        });
    }

    @Test
    void testLoginUserSuccess() throws UserNotFoundException {
        when(userRepository.findByUserEmailAndPassword("user1@gmail.com", "user1")).thenReturn(Optional.of(user1));

        User loggedInUser = userServiceImpl.loginUser(user1);

        assertEquals(user1, loggedInUser);

    }

    @Test
    void testLoginUserFailure() {
        when(userRepository.findByUserEmailAndPassword("user1@gmail.com", "user1")).thenReturn(Optional.empty());

        User user = new User("user1@gmail.com", "user1");
        assertThrows(UserNotFoundException.class, () -> {
            userServiceImpl.loginUser(user);
        });
    }
}