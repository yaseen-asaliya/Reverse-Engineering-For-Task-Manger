package com.exalt.springboot.domain.service;

import com.exalt.springboot.domain.aggregate.Task;

import java.util.List;

public interface ITaskService {

    Task save(Task task);
    Task update(Task task);
    void deleteByID(Long id);
    Task getByID(Long id);
    List<Task> getTasks(Long pageSize, Long pageNumber);
}
