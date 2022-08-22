package com.exalt.springboot.domain.repository;

import com.exalt.springboot.domain.aggregate.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITaskRepository {
    Task findById(int id);

    String saveObject(Task task);

    String deleteById(int taskId);

    List<Task> getTasks(int userId);

    Page<Task> getTasks(int userId, Pageable pageable);
}
