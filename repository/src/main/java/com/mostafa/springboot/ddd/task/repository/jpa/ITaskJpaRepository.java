package com.mostafa.springboot.ddd.task.repository.jpa;

import com.mostafa.springboot.ddd.task.repository.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskJpaRepository extends JpaRepository<TaskEntity, Long> {
}
