package com.example.UserSpringJPA8081.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReactionNotificationDTO {
    private int idReactionStatus;
    private int idUserStatus;
    private int idUserReaction;
    private int idStatus;
    private String nameReaction;
    private Date created;
}
