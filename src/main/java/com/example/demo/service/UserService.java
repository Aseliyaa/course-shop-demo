package com.example.demo.service;


import com.example.demo.exception.UserServiceException;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    boolean register(User user) throws UserServiceException;
    User findByLogin(String login) throws UserServiceException;
    User findByUsername(String userName) throws UserServiceException;

    User findById(long userId) throws UserServiceException;

    void deleteUser(User user) throws UserServiceException;

    List<User> findAllUsers() throws UserServiceException;
}
