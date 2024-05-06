package com.example.demo.exception;

public class CourseServiceException extends Exception {
    public CourseServiceException() {
    }

    public CourseServiceException(String message) {
        super(message);
    }

    public CourseServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseServiceException(Throwable cause) {
        super(cause);
    }
}
