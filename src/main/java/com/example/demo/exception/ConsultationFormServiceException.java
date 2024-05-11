package com.example.demo.exception;

public class ConsultationFormServiceException extends Exception{
    public ConsultationFormServiceException() {
    }

    public ConsultationFormServiceException(String message) {
        super(message);
    }

    public ConsultationFormServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsultationFormServiceException(Throwable cause) {
        super(cause);
    }
}
