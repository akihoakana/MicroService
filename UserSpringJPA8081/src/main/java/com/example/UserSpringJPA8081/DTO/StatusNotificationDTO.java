package com.example.UserSpringJPA8081.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StatusNotificationDTO {
    private int idStatus;
    private int idUser;
    private String detail;
    private Date created;


}
