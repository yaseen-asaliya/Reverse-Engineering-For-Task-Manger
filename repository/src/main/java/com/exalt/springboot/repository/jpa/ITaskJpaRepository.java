package com.exalt.springboot.repository.jpa;

import com.exalt.springboot.repository.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskJpaRepository extends JpaRepository<TaskEntity, Long> {
}
