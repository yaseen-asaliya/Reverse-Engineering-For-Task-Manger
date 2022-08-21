package com.mostafa.springboot.ddd.task.domain.service;

import com.mostafa.springboot.ddd.task.domain.aggregate.Task;

import java.util.List;

public interface ITaskService {

    Task save(Task task);
    Task update(Task task);
    void deleteByID(Long id);
    Task getByID(Long id);
    List<Task> getTasks(Long pageSize, Long pageNumber);
}
