package com.exalt.springboot.service.implementation;

import com.exalt.springboot.domain.aggregate.Task;
import com.exalt.springboot.domain.exception.NotFoundException;
import com.exalt.springboot.domain.repository.ITaskRepository;
import com.exalt.springboot.domain.service.ITaskService;
import com.exalt.springboot.repository.adapter.TaskRepositoryAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImplementation implements ITaskService {
    public final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImplementation.class.getName());

    private ITaskRepository ITaskRepository;

    public TaskServiceImplementation(TaskRepositoryAdapter taskRepositoryAdapter) {
        this.ITaskRepository = taskRepositoryAdapter;
    }

    @Override
    public List<Task> getTasks(int userId) {
        return ITaskRepository.findTasksByUserId(userId);
    }

    @Override
    public Page<Task> getTasks(int userId, Pageable pageable) {
        return ITaskRepository.findTasksByUserIdWithPagination(userId,pageable);
    }

    @Override
    public Task findById(int taskId) {
        Optional<Task> result = Optional.ofNullable(ITaskRepository.findById(taskId));

        Task tempTask = null;
        if(result.isPresent()){
            tempTask = result.get();
        }
        else{
            LOGGER.warn("Wrong id passed.");
            throw new NotFoundException("Task with id -" + taskId + "- not found.");
        }
        LOGGER.debug("The Task was token from database.");
        return tempTask;
    }

    @Override
    public String saveObject(Task task) {
        ITaskRepository.saveObject(task);
        return "Task saved";
    }

    @Override
    public String deleteById(int taskId) {
        ITaskRepository.deleteById(taskId);
        return "Task deleted";
    }
}