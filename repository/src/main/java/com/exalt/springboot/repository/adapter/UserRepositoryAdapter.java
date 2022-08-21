package com.exalt.springboot.repository.adapter;

import com.exalt.springboot.domain.aggregate.User;
import com.exalt.springboot.domain.repository.IUserRepository;
import com.exalt.springboot.repository.entity.UserEntity;
import com.exalt.springboot.repository.jpa.IUserJpaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryAdapter implements IUserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryAdapter.class);

    @Autowired
    private IUserJpaRepository userJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User save(User user) {
        UserEntity userEntity = convertToEntity(user);
        LOGGER.debug("Saving a new user entity : {}", userEntity);
        return convertToModel(userJpaRepository.save(userEntity));
    }

    @Override
    public User update(User user) {
        UserEntity userEntity = convertToEntity(user);
        LOGGER.debug("Updating a user entity : {}", userEntity);
        return convertToModel(userJpaRepository.save(userEntity));
    }

    @Override
    public void deleteByID(Long id) {
        LOGGER.debug("Deleting the user entity with the id equal to {}", id);
        userJpaRepository.deleteById(id);
    }

    private UserEntity convertToEntity(User user){
        return modelMapper.map(user, UserEntity.class);
    }

    private User convertToModel(UserEntity userEntity){
        return modelMapper.map(userEntity, User.class);
    }
}
