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

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements IUserRepository {

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public String saveObject(User user) {
        return null;
    }

    @Override
    public String deleteById(int userId) {
        return null;
    }
}
