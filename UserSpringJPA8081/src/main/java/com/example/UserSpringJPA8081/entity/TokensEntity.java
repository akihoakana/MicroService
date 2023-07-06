package com.example.UserSpringJPA8081.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "tokens")
@Getter
@Setter
public class TokensEntity {
    @Id
    @Column(name = "id_users")
    private int idUsers;

    private String token;

    @Column(name = "token_refresh")
    private String tokenRefresh;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "is_revoked")
    private boolean isRevoked;

    @Column(name = "is_expired")
    private boolean isExpired;

    @JsonIgnore
    @OneToOne()
    @JoinColumn(name = "id_users")
    @MapsId
    private UsersEntity usersEntity;
}
