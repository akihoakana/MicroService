package com.example.StatusAndMess8085.facade;

import com.example.StatusAndMess8085.DTO.UsersDTO;
import com.example.StatusAndMess8085.feignclient.SpringJPAFeignClient;
import com.example.StatusAndMess8085.repository.*;
import com.example.StatusAndMess8085.service.ReactionStatusService;
import com.example.StatusAndMess8085.service.UsersService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserProcessFacade {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private MessRepository messRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ReactionStatusRepository reactionStatusRepository;
    @Autowired
    private ReactionStatusService reactionStatusService;
    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SpringJPAFeignClient springJPAFeignClient;


    @Value("${myHostService.springJPA8081}")
    private String urlHostSpringJPA8081;
    @Value("${myDetailService.springJPA8081.usersDTO}")
    private String urlDetailUsersDTO;

    public List<UsersDTO> usersDTOS(HttpHeaders httpHeaders) {
        JsonNode accounts = springJPAFeignClient.usersDTO(httpHeaders);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(accounts, new TypeReference<>() {
        });
    }
}
