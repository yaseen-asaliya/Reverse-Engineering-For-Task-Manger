package com.exalt.springboot.domain.repository;

import com.exalt.springboot.domain.aggregate.User;

public interface IUserRepository {

    User findById(int id);

    String saveObject(User user);

    String deleteById(int userId);
}
