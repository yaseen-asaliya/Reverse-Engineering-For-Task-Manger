package com.exalt.springboot.domain.repository;

import com.exalt.springboot.domain.aggregate.Task;

import java.util.List;

public interface ITaskRepository {

    Task save(Task task);
    Task update(Task task);
    void deleteByID(Long id);
    Task getByID(Long id);
    List<Task> getTasks(Long pageSize, Long pageNumber);
}
