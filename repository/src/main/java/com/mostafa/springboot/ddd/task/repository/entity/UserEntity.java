package com.mostafa.springboot.ddd.task.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
//TODO: password hashing
public class UserEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Integer age;

    @Column(length = 3000)
    private String tokens;

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

    public String getTokens() {
        return tokens;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }
}
