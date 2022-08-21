package com.mostafa.springboot.ddd.task.domain.repository;

import com.mostafa.springboot.ddd.task.domain.aggregate.Task;

import java.util.List;

public interface ITaskRepository {

    Task save(Task task);
    Task update(Task task);
    void deleteByID(Long id);
    Task getByID(Long id);
    List<Task> getTasks(Long pageSize, Long pageNumber);
}
