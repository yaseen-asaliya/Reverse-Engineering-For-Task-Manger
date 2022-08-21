package com.exalt.springboot.repository.jpa;

import com.exalt.springboot.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserJpaRepository extends JpaRepository<UserEntity, Long> {
}
