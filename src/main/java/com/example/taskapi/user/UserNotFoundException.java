package com.example.taskapi.user;

import com.example.taskapi.common.error.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
