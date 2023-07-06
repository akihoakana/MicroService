package com.example.StatusAndMess8085.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "mess")
public class MessEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "mess_detail")
    private String messDetail;
    private Date time;

//    @JsonIgnore
//    @OneToMany(mappedBy = "messEntity")
//    Set<MessReplyEntity> messReplyEntity;

    @ManyToOne
    @JoinColumn(name = "fromuser_id")
    private UsersEntity fromUser;

    @ManyToOne
    @JoinColumn(name = "touser_id")
    private UsersEntity toUser;
}
