package com.mostafa.springboot.ddd.task.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "task")
public class TaskEntity extends BaseEntity{

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
