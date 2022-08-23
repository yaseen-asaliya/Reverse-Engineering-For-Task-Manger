package com.exalt.springboot.domain.repository;

import com.exalt.springboot.domain.aggregate.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface IUserRepository {

    User findById(int userId);

    String saveObject(User user);

    String deleteById(int userId);

    @Transactional
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
