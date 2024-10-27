package com.todoapplication.todoapplication.controller;

import com.todoapplication.todoapplication.domain.User;
import com.todoapplication.todoapplication.exceptions.UserAlreadyExistsException;
import com.todoapplication.todoapplication.exceptions.UserNotFoundException;
import com.todoapplication.todoapplication.security.ISecurityTokenGenrerator;
import com.todoapplication.todoapplication.services.IUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IUserService userServiceMock;
    private User user;
    private ISecurityTokenGenrerator securityTokenGeneratorMock;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        userServiceMock = mock(IUserService.class);
        securityTokenGeneratorMock = mock(ISecurityTokenGenrerator.class);
        userController = new UserController(userServiceMock, securityTokenGeneratorMock);
    }

    @Test
    public void testSaveUser_Success() throws UserAlreadyExistsException {
        User user = new User("username", "password");

        when(userServiceMock.registerUser(user)).thenReturn(user);

        ResponseEntity<?> response = userController.saveUser(user);

        verify(userServiceMock, times(1)).registerUser(user);
        assert response.getStatusCode() == HttpStatus.ACCEPTED;
        assert response.getBody().equals(user);
    }

    @Test
    public void testSaveUser_UserAlreadyExists() throws UserAlreadyExistsException {
        User user = new User("Prerana@gmail.com", "prerana");

        when(userServiceMock.registerUser(user)).thenThrow(new UserAlreadyExistsException("User already exists"));

        ResponseEntity<?> response = userController.saveUser(user);

        verify(userServiceMock, times(1)).registerUser(user);
        assert response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE;
    }

    @Test
    public void testLogin_Success() throws UserNotFoundException {
        User user = new User("Prerana@gmail.com", "password");
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", "generatedToken");

        when(userServiceMock.loginUser(user)).thenReturn(user);
        when(securityTokenGeneratorMock.generateToken(user)).thenReturn(tokenMap);

        ResponseEntity<?> response = userController.login(user);

        verify(userServiceMock, times(1)).loginUser(user);
        verify(securityTokenGeneratorMock, times(1)).generateToken(user);
        assert response.getStatusCode() == HttpStatus.ACCEPTED;
        assert response.getBody().equals(tokenMap);
    }

    @Test
    public void testLogin_UserNotFound() throws UserNotFoundException {
        User user = new User("nonExistingUsername", "password");

        when(userServiceMock.loginUser(user)).thenThrow(new UserNotFoundException("User not found"));

        ResponseEntity<?> response = userController.login(user);

        verify(userServiceMock, times(1)).loginUser(user);
        assert response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE;
    }
}
