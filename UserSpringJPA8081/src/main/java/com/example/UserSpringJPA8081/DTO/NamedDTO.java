package com.example.UserSpringJPA8081.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NamedDTO {
    private int id;
    private String email;
    private String username;
    private String firstname;
    private String password;
}