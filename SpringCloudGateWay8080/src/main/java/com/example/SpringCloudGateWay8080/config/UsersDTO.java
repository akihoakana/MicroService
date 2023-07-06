package com.example.SpringCloudGateWay8080.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersDTO {
    private int id;
    private String email;
    private String username;
    private String password;
    private boolean verify_active;
}
