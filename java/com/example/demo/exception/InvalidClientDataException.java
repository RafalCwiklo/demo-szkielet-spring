package com.example.demo.exception;

public class InvalidClientDataException extends RuntimeException {

    public InvalidClientDataException(String errorMessage) {
        super("Client creation failed: " + errorMessage);
    }

}
