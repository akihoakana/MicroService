package com.example.UserSpringJPA8081.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "roles")
@Getter
@Setter
public class RolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "rolesEntity")
    private Set<UsersRolesEntity> usersRolesEntities;
}
