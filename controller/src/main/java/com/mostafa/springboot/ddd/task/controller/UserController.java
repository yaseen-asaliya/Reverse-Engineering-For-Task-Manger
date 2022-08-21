package com.mostafa.springboot.ddd.task.controller;

import com.mostafa.springboot.ddd.task.domain.aggregate.User;
import com.mostafa.springboot.ddd.task.domain.service.IUserService;

import com.mostafa.springboot.ddd.task.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity add(@RequestBody UserDTO userDTO){
        try {
            User user = modelMapper.map(userDTO, User.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
        } catch (Exception ex){
            LOGGER.error("An error occurred during saving a new user, {}", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during saving a new user");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            userService.deleteByID(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("The user deleted successfully");
        } catch (Exception ex){
            LOGGER.error("An error occurred during deleting a user, {}", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during deleting a user");
        }
    }
}
