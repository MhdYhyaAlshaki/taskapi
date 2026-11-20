package com.example.taskapi.user;

public class DuplicateUserEmailException extends RuntimeException {

    public DuplicateUserEmailException(Throwable cause) {
        super("A user with this email already exists", cause);
    }
}
