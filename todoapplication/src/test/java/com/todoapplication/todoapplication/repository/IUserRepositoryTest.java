package com.todoapplication.todoapplication.repository;

import com.todoapplication.todoapplication.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IUserRepositoryTest {
    @Autowired
    private IUserRepository userRepository;
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
    public void registerUserSuccess(){
        User addedUser = userRepository.save(user1);
        assertEquals(user1.getUserEmail(),addedUser.getUserEmail());
    }

    @Test
    public void registerUserFailure() {
        User addedUser = userRepository.save(user1);
        assertNotEquals("wrongemail@example.com", addedUser.getUserEmail());
    }
}
