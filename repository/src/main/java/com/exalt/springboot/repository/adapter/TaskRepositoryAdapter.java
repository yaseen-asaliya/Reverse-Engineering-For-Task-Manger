package com.exalt.springboot.repository.adapter;

import com.exalt.springboot.domain.aggregate.Task;
import com.exalt.springboot.domain.repository.ITaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskRepositoryAdapter implements ITaskRepository {

    @Override
    public Task findById(int id) {
        return null;
    }

    @Override
    public String saveObject(Task task) {
        return null;
    }

    @Override
    public String deleteById(int taskId) {
        return null;
    }

    @Override
    public List<Task> getTasks(int userId) {
        return null;
    }

    @Override
    public List<Task> findTasksByUserId(int userId) {
        return null;
    }

    @Override
    public Page<Task> findTasksByUserIdWithPagination(int userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Task> getTasks(int userId, Pageable pageable) {
        return null;
    }
}
