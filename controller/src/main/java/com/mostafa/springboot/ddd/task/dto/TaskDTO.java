package com.mostafa.springboot.ddd.task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDTO extends BaseDTO {

    @NotBlank(message = "description field can't be empty")
    private String description;

    @NotBlank(message = "completed field can't be empty")
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
