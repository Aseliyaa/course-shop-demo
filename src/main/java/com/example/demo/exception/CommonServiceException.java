package com.example.demo.exception;

public class CommonServiceException extends Exception{
    public CommonServiceException() {
    }

    public CommonServiceException(String message) {
        super(message);
    }

    public CommonServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonServiceException(Throwable cause) {
        super(cause);
    }
}
