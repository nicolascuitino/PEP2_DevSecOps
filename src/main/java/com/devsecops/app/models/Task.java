package com.devsecops.app.models;

public class Task {
    private String description;
    private boolean completed;

    public Task() {}

    public Task(String description, boolean completed) {
        this.description = description;
        this.completed = completed;
    }

    // Getters y Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}