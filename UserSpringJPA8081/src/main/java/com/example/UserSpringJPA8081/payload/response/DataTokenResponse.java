package com.example.UserSpringJPA8081.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataTokenResponse {
    private String token;
    private String refreshToken;
}