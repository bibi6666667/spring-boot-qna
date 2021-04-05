package com.codessquad.qna.model.dto;

import com.codessquad.qna.exception.EntityNotCreateException;
import com.codessquad.qna.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto {

    private Long id;

    private String userId;

    private String password;

    private String name;

    private String email;

    public UserDto() {}

    public UserDto(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public User toEntity() {
        if (userId == null || password == null || name == null || email == null) {
            throw new EntityNotCreateException();
        }
        return new User(userId, password, name, email);
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
