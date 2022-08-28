package com.exalt.springboot.service.implementation;

import com.exalt.springboot.domain.UserDetailsImpl;
import com.exalt.springboot.domain.aggregate.User;
import com.exalt.springboot.domain.exception.NotFoundException;
import com.exalt.springboot.domain.repository.IUserRepository;
import com.exalt.springboot.domain.service.IUserService;
import com.exalt.springboot.repository.adapter.UserRepositoryAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImplementation implements IUserService,UserDetailsService {
    public final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplementation.class.getName());
    private IUserRepository iUserRepository;

    public UserServiceImplementation(UserRepositoryAdapter userRepositoryAdapter) {
        this.iUserRepository = userRepositoryAdapter;
    }

    @Override
    public User findById(int userId) {
        Optional<User> result = Optional.ofNullable(iUserRepository.findById(userId));

        User user = null;
        if (result.isPresent()) {
            user = result.get();
        } else {
            LOGGER.warn("Wrong id passed.");
            throw new NotFoundException("User with id -" + userId + "- not found.");
        }
        LOGGER.debug("The User was token from database.");
        return user;
    }

    @Override
    public String saveObject(User user) {
        iUserRepository.saveObject(user);
        return "User saved";
    }

    @Override
    public String deleteById(int userId) {
        iUserRepository.deleteById(userId);
        return "User deleted";
    }

    @Override
    public void resetIsSignout() {

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

}