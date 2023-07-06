package com.example.UserSpringJPA8081.controller;

import com.example.UserSpringJPA8081.facade.SignInFacade;
import com.example.UserSpringJPA8081.payload.request.SignInRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin
@RequestMapping("/signIn")
public class SignInController {

    @Autowired
    private SignInFacade signInFacade;

    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER_DEFAULT')")
    @PostMapping("/customer")
    public String customer() {
        System.out.println("Hello I'm customer");
        return SecurityContextHolder.getContext().toString();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/admin")
    public String admin() {
        System.out.println("Hello I'm admin");
        return SecurityContextHolder.getContext().toString();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    @PostMapping("/manager")
    public String manager() {
        System.out.println("Hello I'm admin");
        return SecurityContextHolder.getContext().toString();
    }

    @RequestMapping("/hello")
    public ResponseEntity<?> helloWord() {
        return ResponseEntity.ok("helloWord I'm Demo");
    }

    @RequestMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam("token") String token) {
        return ResponseEntity.ok(signInFacade.validateToken(token));
    }

    @PostMapping("/regular")
    @PreAuthorize("hasAnyAuthority('regular')")
    public String regular() {
        return SecurityContextHolder.getContext().toString();
    }


    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestParam("refreshToken") String token) {
        return new ResponseEntity<>(signInFacade.refreshToken(token), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> singIn(@RequestBody SignInRequest signInRequest) {
        return new ResponseEntity<>(signInFacade.singIn(signInRequest), HttpStatus.OK);
    }

    @PostMapping("/newpassword/{email}")
    public ResponseEntity<?> newPassword(@RequestParam("password") String password
            , @PathVariable("email") String email) {
        return ResponseEntity.ok(signInFacade.newPassword(email, password));
    }

    @PostMapping("/forgetpassword/{email}")
    public ResponseEntity<?> forgetPasword(@PathVariable("email") String email, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        signInFacade.forgetPasword(email, request);
        return ResponseEntity.ok("Đã gửi mail");
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
//        if(StringUtils.hasText(request.getHeader("Authorization")) && request.getHeader("Authorization").startsWith("Bearer ")){
//            String token =request.getHeader("Authorization").substring(7);
//            System.out.println("token = " + token);
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if (auth != null){
//                new SecurityContextLogoutHandler().logout(request, response, auth);
//            }
//            return ResponseEntity.ok(jwtService.isTokenValid(token));
//        }
//        else
//            return ResponseEntity.ok("không có token");
//    }
}
