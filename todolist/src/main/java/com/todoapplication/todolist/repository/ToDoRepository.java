package com.todoapplication.todolist.repository;

import com.todoapplication.todolist.domain.ToDo;
import com.todoapplication.todolist.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoRepository extends MongoRepository <User,String> {
}
