package com.mostafa.springboot.ddd.task.domain.repository;

import com.mostafa.springboot.ddd.task.domain.aggregate.User;

public interface IUserRepository {

    User save(User user);
    User update(User user);
    void deleteByID(Long id);
    User getByCredentials(String email, String password);
}
