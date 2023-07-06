package com.example.StatusAndMess8085.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "reaction_status")
@Getter
@Setter
public class ReactionStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date created;

    @ManyToOne()
    @JoinColumn(name = "users_id")
    private UsersEntity usersEntity;
    @ManyToOne()
    @JoinColumn(name = "reaction_id")
    private ReactionEntity reactionEntity;
    @ManyToOne()
    @JoinColumn(name = "status_id")
    private StatusEntity statusEntity;
}
