package com.example.StatusAndMess8085.facade;

import com.example.StatusAndMess8085.DTO.RestDTO;
import com.example.StatusAndMess8085.entity.UsersEntity;
import com.example.StatusAndMess8085.feignclient.SpringJPAFeignClient;
import com.example.StatusAndMess8085.repository.*;
import com.example.StatusAndMess8085.service.ReactionStatusService;
import com.example.StatusAndMess8085.service.UsersService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class UserReadFacade {
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

    public RestDTO restDTO(HttpHeaders httpHeaders) {
        return springJPAFeignClient.restDTO(httpHeaders);
    }

    public Optional<UsersEntity> findById(int id) {
        return usersRepository.findById(id);
    }

    public Optional<UsersEntity> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public List<UsersEntity> getUsers() {
        return usersRepository.findAll();
    }
}
