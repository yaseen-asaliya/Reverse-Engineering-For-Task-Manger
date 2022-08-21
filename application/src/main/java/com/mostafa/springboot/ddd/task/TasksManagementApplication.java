package com.mostafa.springboot.ddd.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TasksManagementApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksManagementApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TasksManagementApplication.class, args);
        LOGGER.info("Tasks Management Application Started........");
    }
}
