package com.example.SpringBatch8084.config.Interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "userController", url = "${myHostService.springJPA8081}")
public interface SpringJPAFeignClient {


    @PostMapping(value = "/userController/usersDTO")
    JsonNode usersDTO();
}

