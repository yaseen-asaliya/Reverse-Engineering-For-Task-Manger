package com.mostafa.springboot.ddd.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends BaseDTO {

    @NotBlank(message = "name field can't be empty")
    private String name;

    @NotBlank(message = "email field can't be empty")
    @Email(message = "invalid email address")
    private String email;

    @Size(min = 7, message = "password can't be less than 7")
    @JsonIgnore
    private String password;

    @Min(value = 0, message = "age have to be a positive number")
    private Integer age;

    @JsonIgnore
    @Null(message = "tokens field can't be passed by the apis' client")
    private String[] tokens;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String[] getTokens() {
        return tokens;
    }

    public void setTokens(String[] tokens) {
        this.tokens = tokens;
    }
}
