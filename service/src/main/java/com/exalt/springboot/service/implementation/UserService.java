package com.exalt.springboot.service.implementation;

import com.exalt.springboot.domain.aggregate.User;
import com.exalt.springboot.domain.repository.IUserRepository;
import com.exalt.springboot.domain.service.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IUserRepository userRepository;

    @Override
    public User save(User user) {
        LOGGER.debug("Saving a new user : {}", user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        LOGGER.debug("Updating a user : {}", user);
        return userRepository.update(user);
    }

    @Override
    public void deleteByID(Long id) {
        LOGGER.debug("Deleting the user with the id equal to {}", id);
        userRepository.deleteByID(id);
    }

    @Override
    public User getByCredentials(String email, String password) {
        LOGGER.debug("Getting the user with the email equal to {}", email);
        return userRepository.getByCredentials(email, password);
    }
}
