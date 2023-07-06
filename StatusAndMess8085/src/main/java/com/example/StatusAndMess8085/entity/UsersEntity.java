package com.example.StatusAndMess8085.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "users")
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
    @OneToMany(mappedBy = "usersEntity", fetch = FetchType.EAGER)
    private Set<ReactionStatusEntity> reactionStatusEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "usersEntity", fetch = FetchType.EAGER)
    private Set<StatusEntity> statusEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "fromUser", fetch = FetchType.EAGER)
    private List<MessEntity> fromMess;

    @JsonIgnore
    @OneToMany(mappedBy = "toUser", fetch = FetchType.EAGER)
    private List<MessEntity> toMess;

//    @JsonIgnore
//    @OneToMany(mappedBy = "usersEntity")
//    Set<MessReplyEntity> messReplyEntity;
}
