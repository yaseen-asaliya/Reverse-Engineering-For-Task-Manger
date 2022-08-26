package com.exalt.springboot.repository.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private String email;
    private String username;
    @Column(name = "is_signout")
    private boolean isSignout;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<TaskEntity> tasks;

    public UserEntity() {
    }

    public UserEntity(int id,String name, String password, String email, String username) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.username = username;
        this.isSignout= true;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getSignout() {
        return isSignout;
    }

    public void setSignout(boolean signout) {
        isSignout = signout;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", signout='" + isSignout + '\'' +
                '}';
    }
}
