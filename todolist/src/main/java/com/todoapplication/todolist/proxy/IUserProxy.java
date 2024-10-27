package com.todoapplication.todolist.proxy;

import com.todoapplication.todolist.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="userauthentication",url ="localhost:8083" )
public interface IUserProxy {

    @PostMapping("/api/v2/register")
    public ResponseEntity<?> saveUser(@RequestBody User user);
}
