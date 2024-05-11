package com.example.demo.service.impl;


import com.example.demo.exception.CommonServiceException;
import com.example.demo.exception.CourseServiceException;

import com.example.demo.exception.UserServiceException;
import com.example.demo.exception.ValidatorException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommonService;
import com.example.demo.service.UserService;
import com.example.demo.validator.LoginValidator;
import com.example.demo.validator.PasswordValidator;
import com.example.demo.validator.UsernameValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service

public class UserServiceImpl extends CommonService<User> implements UserService {

    private static String errorMessage;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    Logger logger = LogManager.getLogger();
    public static String getErrorMessage() {
        return errorMessage;
    }
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(User user) throws UserServiceException {
        boolean match = false;
        errorMessage = "";
        try {
            if (PasswordValidator.isValid(user.getPassword())
                    && user.getPassword().equals(user.getRepeatPassword())
                    && LoginValidator.isValid(user.getLogin())
                    && UsernameValidator.isValid(user.getUsername())
                    && user.getCheckboxValue() != null
                    && findByLogin(user.getLogin()) == null
                    && findByUsername(user.getUsername()) == null
            ) {
                logger.log(Level.INFO, "+++++++++++> Data Is Valid");

                Role role = roleRepository.findByName("USER");
                user.setRoles(Collections.singletonList(role));
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                save(user);
                logger.log(Level.INFO, "------------>" + user.getUsername() + " is added");
                match = true;

            } else if (!UsernameValidator.isValid(user.getUsername())) {
                logger.log(Level.INFO, "-----------> Username Is Not Valid");
                errorMessage = UsernameValidator.getMessage();
            } else if (!LoginValidator.isValid(user.getLogin())) {
                logger.log(Level.INFO, "-----------> Login Is Not Valid");
                errorMessage = LoginValidator.getMessage();
            } else if (!PasswordValidator.isValid((user.getPassword()))) {
                logger.log(Level.INFO, "-----------> Password Is Not Valid");
                errorMessage = PasswordValidator.getMessage();
            } else if (!(user.getPassword().equals(user.getRepeatPassword()))) {
                logger.log(Level.INFO, "-----------> Password != Repeat Password");
                errorMessage = "Repeat password correctly";
            } else if (user.getCheckboxValue() == null) {
                logger.log(Level.INFO, "-----------> Checkbox Value is null");
                errorMessage = "Click on checkbox";
            } else if (findByUsername(user.getUsername()) != null) {
                logger.log(Level.INFO, "-----------> Username is already exists");
                errorMessage = "Username is already exists";
            } else if (findByLogin(user.getLogin()) != null) {
                logger.log(Level.INFO, "-----------> Email is already exists");
                errorMessage = "Email is already exists";
            } else {
                logger.log(Level.INFO, "-----------> Error in system");
                errorMessage = "Error in system";
            }
        } catch (ValidatorException | CommonServiceException e) {
            throw new UserServiceException(e);
        }
        return match;
    }

    @Override
    public User findByLogin(String login) throws UserServiceException {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findByUsername(String username) throws UserServiceException {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) throws CommonServiceException {

        return userRepository.save(user);
    }

    @Override
    public boolean delete(User user) throws CommonServiceException {
        return false;
    }

    @Override
    public List<User> findAll() throws CommonServiceException {
        return userRepository.findAll();
    }

    @Override
    public User update(User user) throws CommonServiceException {
        return null;
    }
}
