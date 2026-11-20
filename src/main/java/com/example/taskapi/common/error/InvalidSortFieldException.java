package com.example.taskapi.common.error;

public class InvalidSortFieldException extends RuntimeException {

    public InvalidSortFieldException(String message) {
        super(message);
    }
}
