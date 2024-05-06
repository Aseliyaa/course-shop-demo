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

public class UsernameValidator {
    private static String message = "";
    private static final Logger logger = LogManager.getLogger();
    private static final String regexPattern = "^[a-zA-Z][a-zA-Z0-9_]{6,19}$";

    public static String getMessage() {
        return message;
    }

    public static boolean isValid(String userName) throws ValidatorException {
        boolean isValid = false;

        message = "";
        if (isUsernameValidFormat(userName)) {
            logger.log(Level.INFO, "---------> Username Is Valid");
            isValid = true;
        } else {
            message = "Your username is incorrect";
            logger.log(Level.INFO, "---------> Username Is Incorrect");
        }
        return isValid;
    }

    private static boolean isUsernameValidFormat(String userName) {
        return Pattern.compile(regexPattern)
                .matcher(userName)
                .matches();
    }
}
