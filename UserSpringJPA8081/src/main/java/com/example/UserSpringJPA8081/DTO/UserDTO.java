package com.example.UserSpringJPA8081.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private int id;
    private String email;
    private String username;
    private String password;
    private boolean verify_active;
}
