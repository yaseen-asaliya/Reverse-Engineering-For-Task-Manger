package com.mostafa.springboot.ddd.task.repository.adapter;

import com.mostafa.springboot.ddd.task.domain.aggregate.Task;
import com.mostafa.springboot.ddd.task.domain.repository.ITaskRepository;
import com.mostafa.springboot.ddd.task.repository.entity.TaskEntity;
import com.mostafa.springboot.ddd.task.repository.jpa.ITaskJpaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskRepositoryAdapter implements ITaskRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRepositoryAdapter.class);
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    private ITaskJpaRepository taskJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Task save(Task task) {
        TaskEntity taskEntity = convertToEntity(task);
        LOGGER.debug("Saving a new task entity : {}", taskEntity);
        return convertToModel(taskJpaRepository.save(taskEntity));
    }

    @Override
    public Task update(Task task) {
        TaskEntity taskEntity = convertToEntity(task);
        LOGGER.debug("Updating a task entity : {}", taskEntity);
        return convertToModel(taskJpaRepository.save(taskEntity));
    }

    @Override
    public void deleteByID(Long id) {
        LOGGER.debug("Deleting the task entity with the id equal to {}", id);
        taskJpaRepository.deleteById(id);
    }

    @Override
    public Task getByID(Long id) {
        LOGGER.debug("Getting the task entity with the id equal to {}", id);
        return convertToModel(taskJpaRepository.getOne(id));
    }

    @Override
    public List<Task> getTasks(Long pageSize, Long pageNumber) {
        LOGGER.debug("Getting {} of tasks entities in the page equal to {}", pageSize, pageNumber);
        PageRequest pageRequest = PageRequest.of(pageNumber!=null?pageNumber.intValue():DEFAULT_PAGE_NUMBER
                , pageSize!=null?pageSize.intValue():DEFAULT_PAGE_SIZE);
        return taskJpaRepository.findAll(pageRequest).stream().map(entity -> convertToModel(entity)).collect(Collectors.toList());
    }

    private TaskEntity convertToEntity(Task task){
        return modelMapper.map(task, TaskEntity.class);
    }

    private Task convertToModel(TaskEntity taskEntity){
        return modelMapper.map(taskEntity, Task.class);
    }
}
