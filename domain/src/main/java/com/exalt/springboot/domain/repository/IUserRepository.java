package com.exalt.springboot.domain.repository;

import com.exalt.springboot.domain.aggregate.User;

public interface IUserRepository {

    User save(User user);
    User update(User user);
    void deleteByID(Long id);
    User getByCredentials(String email, String password);
}
