package com.example.StatusAndMess8085.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessDTO {
    private String nameUser;
    private String detail;
    private Date timeSend;
}
