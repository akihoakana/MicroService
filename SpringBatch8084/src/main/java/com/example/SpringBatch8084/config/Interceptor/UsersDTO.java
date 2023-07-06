package com.example.SpringBatch8084.config.Interceptor;

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
