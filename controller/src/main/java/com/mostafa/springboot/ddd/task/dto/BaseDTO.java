package com.mostafa.springboot.ddd.task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Null;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseDTO {

    @Null(message = "id field can't be passed by the apis' client")
    private Long id;

    @Null(message = "createdAt field can't be passed by the apis' client")
    private Date createdAt;

    @Null(message = "updatedAt field can't be passed by the apis' client")
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
