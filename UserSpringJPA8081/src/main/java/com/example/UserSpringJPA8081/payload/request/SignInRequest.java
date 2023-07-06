package com.example.UserSpringJPA8081.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignInRequest {
    @NotBlank(message = "Vui lòng nhập email")
    @Email
    private String email;
    @NotBlank(message = "Vui lòng nhập password")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
            , message = "Dùng ít nhất 8 ký tự, trong đó có chữ hoa, chữ thường , ký tự đặc biệt và số.")
    private String password;
}