package com.exalt.springboot.domain.service;

import com.exalt.springboot.domain.aggregate.User;

public interface IUserService {

    User findById(int userId);

    String saveObject(User user);

    String deleteById(int userId);

}
