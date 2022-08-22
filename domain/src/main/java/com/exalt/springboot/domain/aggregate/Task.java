package com.exalt.springboot.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {

    private int id = 0;
    private User user;
    private String description;
    private int completed;
    private String start;
    private String finish;

    public Task(User user, String description, int completed, String start, String finish) {
        this.user = user;
        this.description = description;
        this.completed = completed;
        this.start = start;
        this.finish = finish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", user=" + user +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", start='" + start + '\'' +
                ", finish='" + finish + '\'' +
                '}';
    }
}
