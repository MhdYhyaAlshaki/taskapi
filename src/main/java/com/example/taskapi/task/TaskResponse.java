package com.example.taskapi.task;

public class TaskResponse {
    private final Long id;
    private final String title;
    private final boolean completed;
    private final Long userId;
    private final String userName;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.completed = task.isCompleted();
        this.userId = task.getUser().getId();
        this.userName = task.getUser().getName();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }
    public Long getUserId() { return userId; }
    public String getUserName() { return userName; }
}
