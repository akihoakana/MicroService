package com.example.StatusAndMess8085.feignclient;

import com.example.StatusAndMess8085.entity.MessEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Component
@FeignClient(name = "statusAndMess", url = "${myHostService.statusAndMess8085}")
public interface StatusAndMessFeignClient {
    @PostMapping(value = "/mess/findById/{id}")
    Optional<MessEntity> findMessById(@PathVariable("id") int id);
}

