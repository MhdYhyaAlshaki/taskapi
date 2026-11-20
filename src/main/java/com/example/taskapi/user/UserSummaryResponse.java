package com.example.taskapi.user;

public class UserSummaryResponse {

    private final Long id;
    private final String name;

    public UserSummaryResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}
