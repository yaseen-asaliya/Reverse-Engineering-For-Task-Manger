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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public TaskRepositoryAdapter(ITaskJpaRepository iTaskJpaRepository, ModelMapper modelMapper) {
        this.iTaskJpaRepository = iTaskJpaRepository;
        this.modelMapper = modelMapper;
    }

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
        iTaskJpaRepository.save(convertToEntity(task));
        return "Task saved";
    }

    @Override
    public String deleteById(int taskId) {
        iTaskJpaRepository.deleteById(taskId);
        return "Task deleted";
    }

    @Override
    public List<Task> getTasks(int userId) {
        return findTasksByUserId(userId);
    }

    @Override
    public Page<Task> getTasks(int userId, Pageable pageable) {
        return findTasksByUserIdWithPagination(userId,pageable);
    }

    @Override
    public List<Task> findTasksByUserId(int userId) {
        return iTaskJpaRepository.findTasksByUserId(userId)
                .stream().map(taskEntity -> convertToModel(taskEntity)).collect(Collectors.toList());
    }

    @Override
    public Page<Task> findTasksByUserIdWithPagination(int userId, Pageable pageable) {
        List<Task> tempListTasks = iTaskJpaRepository.findTasksByUserIdWithPagination(userId, pageable)
                .stream().map(taskEntity -> convertToModel(taskEntity)).collect(Collectors.toList());
        return convertToPage(tempListTasks,pageable);
    }

    public static<T> Page<T> convertToPage(List<T> objectList, Pageable pageable){
        int start = (int) pageable.getOffset();
        int end = Math.min(start+pageable.getPageSize(),objectList.size());
        List<T> subList = start>=end?new ArrayList<>():objectList.subList(start,end);
        return new PageImpl<>(subList,pageable,objectList.size());
    }

    private TaskEntity convertToEntity(Task task) {
        return modelMapper.map(task,TaskEntity.class);
    }

    private User convertToEntity(UserEntity userEntity) {
        return new User(userEntity.getId(),
                userEntity.getName(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getSignout());
    }

    private Task convertToModel(TaskEntity taskEntity) {
        return new Task(taskEntity.getId(),
                convertToEntity(taskEntity.getUser()),
                taskEntity.getDescription(),
                taskEntity.getCompleted(),
                taskEntity.getStart(),
                taskEntity.getFinish());
    }
}
