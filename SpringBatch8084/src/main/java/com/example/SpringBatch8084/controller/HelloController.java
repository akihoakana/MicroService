package com.example.SpringBatch8084.controller;

import com.example.SpringBatch8084.DTO.UserDTO;
import com.example.SpringBatch8084.config.Interceptor.SpecifyHeaderInterceptor;
import com.example.SpringBatch8084.config.Interceptor.UserProcessFacade;
import com.example.SpringBatch8084.feignclient.SpringJPAFeignClient;
import com.example.SpringBatch8084.mapstruct.EmailRoleMapper;
import com.example.SpringBatch8084.payload.request.SignInRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/hello")
public class HelloController {
//    @Value("${school.industry.class.local}")
//    String class1local;
//    @Value("${school.industry.room.local}")
//    String room1local;

    @Autowired
    EmailRoleMapper emailRoleMapper;
    @Autowired
    UserProcessFacade userProcessFacade;
    @Autowired
    private SpringJPAFeignClient springJPAFeignClient;

    @PostMapping("/getDataWithUserEmailDTO")
    public ResponseEntity<?> getDataWithUserEmailDTO(@RequestHeader HttpHeaders httpHeaders) {
        JsonNode accounts = springJPAFeignClient.getDataWithUserEmailDTO(httpHeaders);
        ObjectMapper mapper = new ObjectMapper();
        return ResponseEntity.ok(mapper.convertValue(accounts, new TypeReference<>() {
        }));
    }

    @PostMapping("/addUserGetString")
    public ResponseEntity<?> addUserGetString(@RequestHeader HttpHeaders httpHeaders, @RequestBody SignInRequest signInRequest) {
        String result = springJPAFeignClient.addUserGetString(httpHeaders, signInRequest);
        System.out.println("result = " + result);
        return ResponseEntity.ok(result);
    }

        @PostMapping("/yml")
    public ResponseEntity<?> ymlClone(){
        return ResponseEntity.ok("helloWord I'm Spring Batch ");
    }

//    @SpecifyHeaderInterceptor
//    @PostMapping("/usersDTO")
//    public ResponseEntity<?> usersDTO(@RequestHeader HttpHeaders httpHeaders) {
//        return ResponseEntity.ok(userProcessFacade.usersDTOS(httpHeaders));
//    }
    @SpecifyHeaderInterceptor
    @PostMapping("/usersDTO")
    public ResponseEntity<?> usersDTO() {
        return ResponseEntity.ok(userProcessFacade.usersDTOS());
    }

    @PostMapping("/emailRoleMapper")
    public ResponseEntity<?> emailRoleMapper() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setEmail("đâ@gmail.com");
        userDTO.setUsername("đâ");
        userDTO.setNameRole("Manager");
        return ResponseEntity.ok(emailRoleMapper.userDTOToEmailRole(userDTO));
    }
}
