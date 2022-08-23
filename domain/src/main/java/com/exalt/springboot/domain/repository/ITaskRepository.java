package com.exalt.springboot.domain.repository;

import com.exalt.springboot.domain.aggregate.Task;
import com.exalt.springboot.repository.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITaskRepository {
    Task findById(int id);

    String saveObject(Task task);

    String deleteById(int taskId);

    List<Task> getTasks(int userId);

    List<Task> findTasksByUserId(int userId);

    Page<Task> findTasksByUserIdWithPagination(int userId, Pageable pageable);

    Page<Task> getTasks(int userId, Pageable pageable);
}
