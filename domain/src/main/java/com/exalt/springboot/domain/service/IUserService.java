package com.exalt.springboot.domain.service;

import com.exalt.springboot.domain.aggregate.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {

    User findById(int userId);

    String saveObject(User user);

    String deleteById(int userId);

    UserDetails loadUserByUsername(String username);

    void resetIsSignout();

}
