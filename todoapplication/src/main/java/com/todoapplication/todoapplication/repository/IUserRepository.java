package com.todoapplication.todoapplication.repository;

import com.todoapplication.todoapplication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,String> {
    public Optional<User> findByUserEmailAndPassword(String emailId, String password);
}
