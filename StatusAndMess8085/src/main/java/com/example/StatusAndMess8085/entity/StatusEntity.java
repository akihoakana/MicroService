package com.example.StatusAndMess8085.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity(name = "status")
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String detail;
    private Date created;

    @JsonIgnore
    @OneToMany(mappedBy = "statusEntity", fetch = FetchType.EAGER)
    private Set<ReactionStatusEntity> reactionStatusEntities;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity usersEntity;

}
