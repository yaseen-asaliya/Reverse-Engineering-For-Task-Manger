package com.exalt.springboot.repository.adapter;

import com.exalt.springboot.domain.aggregate.Task;
import com.exalt.springboot.domain.aggregate.User;
import com.exalt.springboot.domain.exception.NotFoundException;
import com.exalt.springboot.domain.repository.ITaskRepository;
import com.exalt.springboot.repository.entity.TaskEntity;
import com.exalt.springboot.repository.entity.UserEntity;
import com.exalt.springboot.repository.jpa.ITaskJpaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskRepositoryAdapter implements ITaskRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRepositoryAdapter.class);

    @Autowired
    private ITaskJpaRepository iTaskJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Task findById(int taskId) {
        Optional<Task> result = Optional.ofNullable(convertToModel(iTaskJpaRepository.findById(taskId).get()));

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
        TaskEntity taskEntity = convertToEntity(task);
        taskEntity.getUser().setSignout(false);
        System.out.println(taskEntity);
        System.out.println(task);
        iTaskJpaRepository.save(taskEntity);
        return "Task saved";
    }

    @Override
    public String deleteById(int taskId) {
        iTaskJpaRepository.deleteById(taskId);
        return "Task deleted";
    }

    @Override
    public List<Task> getTasks(int userId) {
        return this.findTasksByUserId(userId);
    }

    @Override
    public Page<Task> getTasks(int userId, Pageable pageable) {
        return this.findTasksByUserIdWithPagination(userId,pageable);
    }

    @Override
    public List<Task> findTasksByUserId(int userId) {
        return iTaskJpaRepository.findTasksByUserId(userId)
                .stream().map(taskEntity -> convertToModel(taskEntity)).collect(Collectors.toList());
    }

    @Override
    public Page<Task> findTasksByUserIdWithPagination(int userId, Pageable pageable) {
        return (Page<Task>) iTaskJpaRepository.findTasksByUserIdWithPagination(userId, pageable)
                .stream().map(taskEntity -> convertToModel(taskEntity)).collect(Collectors.toList());
    }

    private TaskEntity convertToEntity(Task task){
        return new TaskEntity(task.getId(),
                convertToEntity(task.getUser()),
                task.getDescription(),
                task.getCompleted(),
                task.getStart(),
                task.getFinish());
    }

    private UserEntity convertToEntity(User user) {
        return new UserEntity(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getUsername());
    }

    private User convertToEntity(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getName(), userEntity.getPassword(), userEntity.getEmail(), userEntity.getUsername());
    }

    private Task convertToModel(TaskEntity taskEntity){
        return new Task(convertToEntity(taskEntity.getUser()),
                taskEntity.getDescription(),
                taskEntity.getCompleted(),
                taskEntity.getStart(),
                taskEntity.getFinish());
    }
}
