package com.example.demo.exception;

public class CartServiceException extends Exception{
    public CartServiceException() {
    }

    public CartServiceException(String message) {
        super(message);
    }

    public CartServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CartServiceException(Throwable cause) {
        super(cause);
    }
}
