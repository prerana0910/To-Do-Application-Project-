package com.todoapplication.todolist.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class User {
    @Id
    private String userEmail;
    private String userName;
    @Transient
    private String password;
    private String phoneNo;
    private List<ToDo> toDo;
    public User() {
    }

    public User(String userEmail, String userName, String password, String phoneNo, List<ToDo> toDo) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.password = password;
        this.phoneNo = phoneNo;
        this.toDo = toDo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public List<ToDo> getToDo() {
        return toDo;
    }

    public void setToDo(List<ToDo> toDo) {
        this.toDo = toDo;
    }

    @Override
    public String toString() {
        return "User{" +
                "userEmail='" + userEmail + '\'' +
                ", UserName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phNo='" + phoneNo + '\'' +
                ", toDo=" + toDo +
                '}';
    }
}
