package com.example.UserSpringJPA8081.payload.response;

import com.example.UserSpringJPA8081.entity.UsersEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
    private UsersEntity usersEntity;
    private String linkActive;
}
