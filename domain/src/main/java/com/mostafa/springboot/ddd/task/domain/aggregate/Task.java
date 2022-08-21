package com.mostafa.springboot.ddd.task.domain.aggregate;

public class Task extends Base {

    private String description;
    private Boolean completed;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
