package com.mostafa.springboot.ddd.task.domain.aggregate;

public class User extends Base {

    private String name;
    private String email;
    private String password;
    private Integer age;
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
