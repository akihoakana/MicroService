package com.example.SpringBatch8084.config.Interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProcessFacade {

    @Autowired
    private SpringJPAFeignClient springJPAFeignClient;


    public List<UsersDTO> usersDTOS() {
        JsonNode accounts = springJPAFeignClient.usersDTO();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(accounts, new TypeReference<>() {
        });
    }
}
