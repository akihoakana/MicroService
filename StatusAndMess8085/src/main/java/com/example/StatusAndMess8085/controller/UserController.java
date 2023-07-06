package com.example.StatusAndMess8085.controller;

//import com.example.StatusAndMess8085.config.RabbitMQReceive;

import com.example.StatusAndMess8085.facade.UserProcessFacade;
import com.example.StatusAndMess8085.facade.UserReadFacade;
import com.example.StatusAndMess8085.facade.UserWriterFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserReadFacade userReadFacade;
    @Autowired
    private UserProcessFacade userProcessFacade;
    @Autowired
    private UserWriterFacade userWriterFacade;


    @RequestMapping("/hello")
    public ResponseEntity<?> helloWord() {
        return ResponseEntity.ok("helloWord I'm users");
    }

    @RequestMapping("/restDTO")
    public ResponseEntity<?> restDTO(@RequestHeader HttpHeaders httpHeaders) {
        return ResponseEntity.ok(userReadFacade.restDTO(httpHeaders));
    }

    @RequestMapping("/usersDTO")
    public ResponseEntity<?> usersDTO(@RequestHeader HttpHeaders httpHeaders) {
        return ResponseEntity.ok(userProcessFacade.usersDTOS(httpHeaders));
    }

    @PostMapping("/deleteAllUser")
    public ResponseEntity<?> deleteAllUser() {
        userWriterFacade.deleteAllUser();
        return ResponseEntity.ok("Đã delete all user");
    }

    @PostMapping("/getUserDTOAndSynDB")
    public ResponseEntity<?> getUserDTO(@RequestHeader HttpHeaders httpHeaders) {
        userWriterFacade.getUserDTO(httpHeaders);
        return ResponseEntity.ok(userReadFacade.getUsers());
    }

    @PostMapping("/deleteUsersById/{id}")
    public ResponseEntity<?> deleteUsersById(@PathVariable int id) {
        userWriterFacade.deleteUsersById(id);
        return ResponseEntity.ok("Đã delete user " + id);
    }

    @PostMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return ResponseEntity.ok(userReadFacade.findById(id));
    }

    @PostMapping("/findByEmail")
    public ResponseEntity<?> findByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userReadFacade.findByEmail(email));
    }

    @PostMapping("/updateUsers")
    public ResponseEntity<?> updateUsers(@RequestParam("id") int id
            , @RequestParam("email") String email
            , @RequestParam("username") String username
            , @RequestParam("password") String password
            , @RequestParam("verify_active") boolean verify_active) {
        userWriterFacade.updateUsers(id, email, username, password, verify_active);
        return ResponseEntity.ok("Đã update user " + id);
    }

    @PostMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userReadFacade.getUsers());
    }

}
