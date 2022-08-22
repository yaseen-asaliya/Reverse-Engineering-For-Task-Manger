package com.exalt.springboot.domain.service;

import com.exalt.springboot.domain.aggregate.User;

public interface IUserService {

    User findById(int id);

    String saveObject(User user);

    String deleteById(int userId);

}
