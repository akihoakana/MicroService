package com.example.UserSpringJPA8081.entity.multiIdEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users_Roles implements Serializable {
    private int usersId;
    private int rolesId;
}
