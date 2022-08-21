package com.mostafa.springboot.ddd.task.repository.jpa;

import com.mostafa.springboot.ddd.task.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserJpaRepository extends JpaRepository<UserEntity, Long> {
}
