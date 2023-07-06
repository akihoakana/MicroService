package com.example.UserSpringJPA8081.facade;

import com.example.UserSpringJPA8081.entity.UsersEntity;
import com.example.UserSpringJPA8081.payload.request.SignInRequest;
import com.example.UserSpringJPA8081.payload.response.DataResponse;
import com.example.UserSpringJPA8081.payload.response.RegisterResponse;
import com.example.UserSpringJPA8081.service.RegisterService;
import com.example.UserSpringJPA8081.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class RegisterFacade {

    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    UsersService usersService;
    @Autowired
    private RegisterService registerService;

    public DataResponse register(SignInRequest signInRequest, HttpServletRequest request) throws IOException, MessagingException {
        String verifyURL = request.getRequestURL().toString()
                .replace(request.getServletPath(), "").replace("8081", "8080") + "/register/verify/" + signInRequest.getEmail();
        UsersEntity usersEntity = registerService.registerNewUserAccount(signInRequest, verifyURL);
        DataResponse dataResponse = new DataResponse();
        dataResponse.setStatus(HttpStatus.OK.value());
        dataResponse.setSuccess(true);
        dataResponse.setDesc("");
        if (usersEntity == null) {
            System.out.println("There is an account with that email address: "
                    + signInRequest.getEmail());
            dataResponse.setData("There is an account with that email address: "
                    + signInRequest.getEmail());
        } else {
            RegisterResponse registerResponseModel = new RegisterResponse();
            registerResponseModel.setUsersEntity(usersEntity);
            registerResponseModel.setLinkActive(verifyURL);
            dataResponse.setData(registerResponseModel);
        }
        return dataResponse;
    }

    public DataResponse resendConfirmByEmail(String email, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        DataResponse dataResponse = new DataResponse();
        dataResponse.setStatus(HttpStatus.OK.value());
        dataResponse.setSuccess(true);
        dataResponse.setDesc("");
        UsersEntity usersEntity = usersService.getUsers(email);
        if (usersEntity == null) {
            System.out.println("Tài khoản không tồn tại");
            dataResponse.setData("Tài khoản không tồn tại");
        } else {
            System.out.println("Tài khoản đã được gửi mail xác nhận");
            String verifyURL = request.getRequestURL().toString()
                    .replace(request.getServletPath(), "").replace("8081", "8080") + "/register/verify/" + email;
            registerService.sendVerificationEmail(email, verifyURL);
            dataResponse.setData(verifyURL);
        }
        return dataResponse;
    }

    public DataResponse confirmByEmail(String email) {
        DataResponse dataResponse = new DataResponse();
        dataResponse.setStatus(HttpStatus.OK.value());
        dataResponse.setSuccess(true);
        dataResponse.setDesc("");
        UsersEntity usersEntity = registerService.confirmByEmail(email);
        if (usersEntity == null) {
            System.out.println("Tài khoản không tồn tại");
            dataResponse.setData("Tài khoản không tồn tại");
        } else {
            System.out.println("Tài khoản đã được xác nhận");
            dataResponse.setData(usersEntity);
        }
        return dataResponse;
    }
}
