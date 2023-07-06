package com.example.StatusAndMess8085.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class MessNotificationDTO {
    private int idMess;
    private int idFromUser;
    private int idToUser;
    private String detail;
    private Date created;
}
