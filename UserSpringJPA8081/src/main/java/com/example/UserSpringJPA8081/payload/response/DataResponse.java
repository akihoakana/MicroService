package com.example.UserSpringJPA8081.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse {
    private int status;
    private boolean isSuccess;
    private String desc;
    private Object data;
}
