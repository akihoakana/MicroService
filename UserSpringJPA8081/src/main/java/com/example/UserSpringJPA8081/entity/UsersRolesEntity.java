package com.example.UserSpringJPA8081.entity;

import com.example.UserSpringJPA8081.entity.multiIdEntity.Users_Roles;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "users_roles")
@Getter
@Setter
@IdClass(Users_Roles.class)
public class UsersRolesEntity {
    @Id
    @Column(name = "users_id")
    private int usersId;

    //    @Id
//    @Column(name = "roles_id")
//    @Enumerated(EnumType.ORDINAL)
//    private UserRolesEnum userRolesEnum;
    @Id
    @Column(name = "roles_id")
//    @Enumerated(EnumType.ORDINAL)
    private int rolesId;

    @ManyToOne()
    @JoinColumn(name = "users_id", insertable = false, updatable = false)
    private UsersEntity usersEntity;

    @OneToOne()
    @JoinColumn(name = "roles_id", insertable = false, updatable = false)
    private RolesEntity rolesEntity;
}
