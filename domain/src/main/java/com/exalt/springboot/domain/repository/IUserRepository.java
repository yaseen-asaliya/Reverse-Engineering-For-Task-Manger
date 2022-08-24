package com.exalt.springboot.domain.repository;

import com.exalt.springboot.domain.aggregate.User;

import java.util.Optional;

public interface IUserRepository {

    User findById(int userId);

    String saveObject(User user);

    String deleteById(int userId);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
