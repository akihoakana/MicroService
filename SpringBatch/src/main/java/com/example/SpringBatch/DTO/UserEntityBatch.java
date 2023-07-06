package com.example.SpringBatch.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserEntityBatch {
    private int id;
    private String email;
    private String username;
    private String fullname;
    private String password;
}