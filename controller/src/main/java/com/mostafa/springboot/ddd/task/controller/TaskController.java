package com.mostafa.springboot.ddd.task.controller;

import com.mostafa.springboot.ddd.task.domain.aggregate.Task;
import com.mostafa.springboot.ddd.task.domain.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private ITaskService taskService;

    @PostMapping()
    public ResponseEntity add(@RequestBody Task task){
        try {
            task.setId(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(task));
        } catch (Exception ex){
            LOGGER.error("An error occurred during saving a new task, {}", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during saving a new task");
        }
    }

    @PutMapping()
    public ResponseEntity update(@RequestBody Task task){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(taskService.update(task));
        } catch (Exception ex){
            LOGGER.error("An error occurred during updating a task, {}", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during updating a task");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            taskService.deleteByID(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("The task deleted successfully");
        } catch (Exception ex){
            LOGGER.error("An error occurred during deleting a task, {}", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during deleting a task");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getByID(@PathVariable Long id){
        try {
            return ResponseEntity.ok(taskService.getByID(id));
        } catch (Exception ex){
            LOGGER.error("An error occurred during getting a task, {}", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during getting a task");
        }
    }

    @GetMapping()
    public ResponseEntity get(@RequestParam(required = false) Long pageSize, @RequestParam(required = false) Long pageNumber){
        try {
            return ResponseEntity.ok(taskService.getTasks(pageSize, pageNumber));
        } catch (Exception ex){
            LOGGER.error("An error occurred during getting list of tasks, {}", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during getting list of tasks");
        }
    }
}
