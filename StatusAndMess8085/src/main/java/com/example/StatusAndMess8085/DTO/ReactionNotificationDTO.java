package com.example.StatusAndMess8085.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ReactionNotificationDTO {
    private int idReactionStatus;
    private int idUserStatus;
    private int idUserReaction;
    private int idStatus;
    private String nameReaction;
    private Date created;
}
