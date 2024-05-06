package com.example.demo.exception;

public class CategoryServiceException extends Exception{
    public CategoryServiceException() {
    }

    public CategoryServiceException(String message) {
        super(message);
    }

    public CategoryServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryServiceException(Throwable cause) {
        super(cause);
    }
}
