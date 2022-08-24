package com.exalt.springboot.repository.adapter;

import com.exalt.springboot.domain.aggregate.User;
import com.exalt.springboot.domain.exception.NotFoundException;
import com.exalt.springboot.domain.repository.IUserRepository;
import com.exalt.springboot.repository.entity.UserEntity;
import com.exalt.springboot.repository.jpa.IUserJpaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements IUserRepository {

    public final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryAdapter.class);

    @Autowired
    private IUserJpaRepository userJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User findById(int userId) {
        Optional<UserEntity> temp = userJpaRepository.findById(userId);
        Optional<User> result = Optional.ofNullable(convertToModel(temp.get()));

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
        UserEntity userEntity = convertToEntity(user);
        LOGGER.debug("Saving a new user entity : {}", userEntity);
        userJpaRepository.save(userEntity);
        return "User saved";
    }

    @Override
    public String deleteById(int userId) {
        userJpaRepository.deleteById(userId);
        return "User deleted";
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    private UserEntity convertToEntity(User user){
        return modelMapper.map(user, UserEntity.class);
    }

    private User convertToModel(UserEntity userEntity){
        return modelMapper.map(userEntity, User.class);
    }

}
