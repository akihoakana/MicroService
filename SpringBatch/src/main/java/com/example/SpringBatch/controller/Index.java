package com.example.SpringBatch.controller;

import com.example.SpringBatch.DTO.UserDTO;
import com.example.SpringBatch.entity.RolesEntity;
import com.example.SpringBatch.entity.UsersEntity;
import com.example.SpringBatch.repository.RolesRepository;
import com.example.SpringBatch.repository.UsersRepository;
import com.example.SpringBatch.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Index {
     private static Logger logger = LogManager.getLogger(Index.class);

     @Autowired
     private RolesRepository rolesRepository;
     @Autowired
     private UsersRepository usersRepository;
     @Autowired
     private UserService userService;
//     @Autowired
//     private SendMailService sendMailService;


    @PostMapping("/hello")
    public ResponseEntity<?> helloWord(){
        return ResponseEntity.ok("helloWord I'm Spring Batch");
    }
    @GetMapping("/hello")
    public ResponseEntity<?> helloGetWord(){
        return ResponseEntity.ok("helloWord I'm Spring Batch");
    }
    @PostMapping("/roles")
    public ResponseEntity<?> getRoles(){
        return ResponseEntity.ok(rolesRepository.findAll());
    }

//    @PostMapping("/selectUserDTOBatch")
//    public ResponseEntity<?> selectUserDTOBatch(){
//        return ResponseEntity.ok(usersRepository.selectUserDTOBatch());
//    }
    @PostMapping("/users")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(usersRepository.findAll());
    }

    @PostMapping("/usersDTO")
    public ResponseEntity<?> getUsersDTO(){
        List<UserDTO> userDTOS = new ArrayList<>();
        usersRepository.findAll().forEach(usersEntity -> {
            UserDTO userDTO =new UserDTO();
            userDTO.setId(usersEntity.getId());
            userDTO.setEmail(usersEntity.getEmail());
            userDTO.setUsername(usersEntity.getUsername());
            RolesEntity roles = usersEntity.getRoles();
            if (roles!=null)
                userDTO.setNameRole(roles.getName());
            else
                userDTO.setNameRole("");
            userDTOS.add(userDTO);
        });
        return ResponseEntity.ok(userDTOS);
    }
    @PostMapping("/updateFullName")
    public ResponseEntity<?> updateFullNameByUserId(
            @RequestParam(name = "fullName")String fullName,
            @RequestParam(name = "userId")int userId){
        return ResponseEntity.ok(userService.updateFullNameByUserId(fullName,userId));
    }
    @PostMapping("/updateRoleId")
    public ResponseEntity<?> updateRoleIdByUserId(
            @RequestParam(name = "roleId")int roleId,
            @RequestParam(name = "userId")int userId){
        return ResponseEntity.ok(usersRepository.updateRoleIdByUserId(roleId,userId));
    }
}
