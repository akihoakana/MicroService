package com.example.UserSpringJPA8081.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "notification")
@Getter
@Setter
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String detail;
    private Date created;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UsersEntity usersEntity;
}
