package com.example.taskapi.task;

import com.example.taskapi.user.UserSummaryResponse;

public class TaskResponse {
    private final Long id;
    private final String title;
    private final boolean completed;
    private final UserSummaryResponse user;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.completed = task.isCompleted();
        this.user = new UserSummaryResponse(task.getUser());
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }
    public UserSummaryResponse getUser() { return user; }
}
