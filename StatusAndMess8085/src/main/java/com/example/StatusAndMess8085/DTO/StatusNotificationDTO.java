package com.example.StatusAndMess8085.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class StatusNotificationDTO {
    private int idStatus;
    private int idUser;
    private String detail;
    private Date created;


}
