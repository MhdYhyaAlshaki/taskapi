package com.example.taskapi.task;

import com.example.taskapi.common.error.NotFoundException;

public class TaskNotFoundException extends NotFoundException {
    public TaskNotFoundException(Long id) {
        super("Task not found with id: " + id);
    }
}
