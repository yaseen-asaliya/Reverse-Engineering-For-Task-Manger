package com.exalt.springboot.domain.service;

import com.exalt.springboot.domain.aggregate.User;

public interface IUserService {

    User save(User user);
    User update(User user);
    void deleteByID(Long id);

}
