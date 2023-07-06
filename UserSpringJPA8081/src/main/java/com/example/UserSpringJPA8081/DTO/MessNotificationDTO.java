package com.example.UserSpringJPA8081.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessNotificationDTO {
    private int idMess;
    private int idFromUser;
    private int idToUser;
    private String detail;
    private Date created;
}
