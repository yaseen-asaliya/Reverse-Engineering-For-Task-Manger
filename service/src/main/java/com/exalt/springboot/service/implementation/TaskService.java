package com.exalt.springboot.service.implementation;

import com.exalt.springboot.domain.aggregate.Task;
import com.exalt.springboot.domain.repository.ITaskRepository;
import com.exalt.springboot.domain.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private ITaskRepository taskRepository;

    @Override
    public Task save(Task task) {
        LOGGER.debug("Saving a new task : {}", task);
        return taskRepository.save(task);
    }

    @Override
    public Task update(Task task) {
        LOGGER.debug("Updating a task : {}", task);
        return taskRepository.update(task);
    }

    @Override
    public void deleteByID(Long id) {
        LOGGER.debug("Deleting the task with the id equal to {}", id);
        taskRepository.deleteByID(id);
    }

    @Override
    public Task getByID(Long id) {
        LOGGER.debug("Getting the task with the id equal to {}", id);
        return taskRepository.getByID(id);
    }

    @Override
    public List<Task> getTasks(Long pageSize, Long pageNumber) {
        LOGGER.debug("Getting {} of tasks in the page equal to {}", pageSize, pageNumber);
        return taskRepository.getTasks(pageSize, pageNumber);
    }
}
