package com.example.demo.exception;

public class StripeServiceException extends Exception{
    public StripeServiceException() {
    }

    public StripeServiceException(String message) {
        super(message);
    }

    public StripeServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public StripeServiceException(Throwable cause) {
        super(cause);
    }
}
