package com.example.UserSpringJPA8081.entity;

import com.example.UserSpringJPA8081.DTO.NamedDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@NamedNativeQuery(name = "UsersEntity.selectUser",
        query = "select  users.id,users.email,users.username,users.firstname ,users.password from users ",
        resultSetMapping = " 1")
@SqlResultSetMapping(name = " 1",
        classes = @ConstructorResult(targetClass = NamedDTO.class,
                columns = {
                        @ColumnResult(name = " id", type = int.class),
                        @ColumnResult(name = " email", type = String.class),
                        @ColumnResult(name = " username", type = String.class),
                        @ColumnResult(name = " firstname", type = String.class),
                        @ColumnResult(name = " password", type = String.class),
                }))
@Entity(name = "users")
@Getter
@Setter
@ToString
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String username;
    private String password;
    private boolean verify_active;
    private String firstname;
    private String lastname;
    private String phone;
    private String avatar;

    @JsonIgnore
    @OneToMany(mappedBy = "usersEntity")
    private Set<UsersRolesEntity> usersRolesEntities;

    @OneToOne(mappedBy = "usersEntity", cascade = {CascadeType.ALL})
    @PrimaryKeyJoinColumn
    private TokensEntity tokensEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "usersEntity")
    private Set<NotificationEntity> notificationEntities;
}
