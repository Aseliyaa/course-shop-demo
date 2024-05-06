package com.example.demo.exception;

public class RoleServiceException extends Exception{
    public RoleServiceException() {
    }

    public RoleServiceException(String message) {
        super(message);
    }

    public RoleServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleServiceException(Throwable cause) {
        super(cause);
    }
}
