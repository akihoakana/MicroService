package com.example.UserSpringJPA8081.controller;

import com.example.UserSpringJPA8081.facade.RegisterFacade;
import com.example.UserSpringJPA8081.payload.request.SignInRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    RegisterFacade registerFacade;

    @PostMapping("")
    public ResponseEntity<?> register(@Valid @RequestBody SignInRequest signInRequest, HttpServletRequest request) throws IOException, MessagingException {
        return ResponseEntity.ok(registerFacade.register(signInRequest, request));
    }

    @PostMapping("/resend/{email}")
    public ResponseEntity<?> resendConfirmByEmail(@PathVariable("email") String email, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        return ResponseEntity.ok(registerFacade.resendConfirmByEmail(email, request));
    }

    @RequestMapping("/verify/{email}")
    public ResponseEntity<?> confirmByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(registerFacade.confirmByEmail(email));
    }

}
