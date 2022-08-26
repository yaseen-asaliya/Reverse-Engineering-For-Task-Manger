package com.exalt.springboot.repository.jpa;

import com.exalt.springboot.domain.aggregate.User;
import com.exalt.springboot.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IUserJpaRepository extends JpaRepository<UserEntity, Integer> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update UserEntity set isSignout = true")
    void resetIsSignout();
}
