package com.example.demo.validator;


import com.example.demo.entity.User;

import com.example.demo.exception.ServiceException;
import com.example.demo.exception.ValidatorException;
import com.example.demo.service.CommonService;
import com.example.demo.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.regex.Pattern;

public class LoginValidator {
    private static final Logger logger = LogManager.getLogger();
    private static final String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static String message = "";

    public static String getMessage() {
        return message;
    }

    public static boolean isValid(String login) throws ValidatorException {
        boolean isValid = false;

        message = "";
        if (isLoginValidFormat(login)) {
            isValid = true;
            logger.log(Level.INFO, "---------> Login Is Valid");
        } else {
            message = "Your email address is incorrect";
            logger.log(Level.INFO, "---------> Login Is Incorrect");
        }
        return isValid;
    }

    private static boolean isLoginValidFormat(String login){
        return Pattern.compile(regexPattern)
                .matcher(login)
                .matches();
    }

}
