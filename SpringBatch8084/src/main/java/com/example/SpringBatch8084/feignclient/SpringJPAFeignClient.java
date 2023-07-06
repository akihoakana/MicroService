package com.example.SpringBatch8084.feignclient;

import com.example.SpringBatch8084.payload.request.SignInRequest;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "springJPA", url = "http://localhost:8081/user")
public interface SpringJPAFeignClient {
    @PostMapping(value = "/SpringJPA/getDataWithUserEmailDTO")
    JsonNode getDataWithUserEmailDTO(@RequestHeader HttpHeaders httpHeaders);

    @PostMapping(value = "/SpringJPA/addUserGetString")
    String addUserGetString(@RequestHeader HttpHeaders httpHeaders, @RequestBody SignInRequest signInRequest);
}

