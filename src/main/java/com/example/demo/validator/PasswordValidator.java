package com.example.demo.validator;

import java.io.Serializable;

public class PasswordValidator implements Serializable {
    private static String message = "";
    static final int MAX = 8;
    static final int MIN_Uppercase = 2;
    static final int MIN_Lowercase = 2;
    static final int NUM_Digits = 2;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        PasswordValidator.message = message;
    }

    public static boolean isValid(String password){
        int uppercaseCounter = 0;
        int lowercaseCounter = 0;
        int digitCounter = 0;

        boolean isValid = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c))
                uppercaseCounter++;
            else if (Character.isLowerCase(c))
                lowercaseCounter++;
            else if (Character.isDigit(c))
                digitCounter++;
        }

        if(password.length() >= MAX && uppercaseCounter >= MIN_Uppercase
        && lowercaseCounter >= MIN_Lowercase && digitCounter >= NUM_Digits){

            isValid = true;
        } else {
            message = "";
            message += "Your password does not contain the following: ";
            if (password.length() <= MAX)
                message += "atleast 8 characters";
            else if (lowercaseCounter < MIN_Lowercase)
                message += "Minimum lowercase letters";
            else if (uppercaseCounter < MIN_Uppercase)
                message += "Minimum uppercase letters";
            else if (digitCounter < NUM_Digits)
                message += "Minimum number of numeric digits";
            setMessage(message);
        }

        return isValid;
    }
}
