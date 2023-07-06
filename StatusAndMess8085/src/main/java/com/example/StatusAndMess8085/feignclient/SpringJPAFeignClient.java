package com.example.StatusAndMess8085.feignclient;

import com.example.StatusAndMess8085.DTO.RestDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "userController", url = "${myHostService.springJPA8081}")
public interface SpringJPAFeignClient {
    @PostMapping(value = "/userController/RestDTO")
    RestDTO restDTO(@RequestHeader HttpHeaders httpHeaders);

    @PostMapping(value = "/userController/usersDTO")
    JsonNode usersDTO(@RequestHeader HttpHeaders httpHeaders);
}

