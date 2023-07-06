package com.example.StatusAndMess8085.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DetailUsersReactionModel {
    private int id;
    private String nameUserReactionModel;
    private Date timeReaction;
    private String typeReactionModel;
}
