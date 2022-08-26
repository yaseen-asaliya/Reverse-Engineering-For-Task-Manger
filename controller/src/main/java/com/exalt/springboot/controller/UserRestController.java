package com.exalt.springboot.controller;

import com.exalt.springboot.domain.aggregate.User;
import com.exalt.springboot.domain.exception.NotFoundException;
import com.exalt.springboot.domain.service.IUserService;
import com.exalt.springboot.dto.UserDTO;
import com.exalt.springboot.repository.entity.UserEntity;
import com.exalt.springboot.repository.jpa.IUserJpaRepository;
import com.exalt.springboot.security.jwt.AuthTokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserRestController {
    public final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class.getName());
    private IUserService userService;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Autowired
    private IUserJpaRepository userRepository;

    @Autowired
    public UserRestController(IUserService userService) {
        this.userService = userService;
        LOGGER.info("User Controller created successfully");
    }

    // Get current user information
    @GetMapping("/user")
    public User getCurrentUser(){
        checkIfLogin();
        int userId = authTokenFilter.getUserId();
        return userService.findById(userId);
    }

    // Update current user information
    @PutMapping("/user")
    public String updateUser(@RequestBody UserDTO userDTO){
        checkIfLogin();
        User user = convertToModel(userDTO);
        int userId = authTokenFilter.getUserId();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(userId);
        userService.saveObject(user);
        LOGGER.debug("User updated completed.");
        return user + " updated successfully.";
    }

    // Delete Current user
    @DeleteMapping("/user")
    public String deleteUser(){
        checkIfLogin();
        int userId = authTokenFilter.getUserId();
        User tempUser = (User)userService.findById(userId);
        userService.deleteById(userId);
        LOGGER.debug("User deleted completed.");
        return tempUser.toString() + " deleted successfully.";
    }

    @PreDestroy
    public boolean resetIsSignoutColumn() {
        LOGGER.info("Reset all users to signout.");
        try{
            userRepository.resetIsSignout();
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private boolean isSignout() {
        int userId = authTokenFilter.getUserId();
        Optional<User> user = Optional.of(convertToModel(userRepository.findById(userId).get()));
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        if (user.get().isSignout()) {
            return true;
        }
        return false;
    }

    private void checkIfLogin(){
        if(isSignout()){
            throw new RuntimeException("You're unauthorized");
        }
    }

    private User convertToModel(UserEntity userEntity){
        return new User(userEntity.getId(),
                userEntity.getName(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getSignout());
    }

    private User convertToModel(UserDTO userDto){
        return new User(0,
                userDto.getName(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getUsername(),
                false);
    }
}