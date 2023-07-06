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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
public class UserWriterFacade {
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

    public void deleteAllUser() {
        usersService.deleteAllUser();
    }

    public void deleteUsersById(int id) {
        usersRepository.deleteById(id);
    }

    public void updateUsers(int id
            , String email
            , String username
            , String password
            , boolean verify_active) {
        usersRepository.updateUsers(id, email, username, password, verify_active);
    }

    public void getUserDTO(HttpHeaders httpHeaders) {
        usersService.deleteAllUser();
        System.out.println("url = " + urlHostSpringJPA8081 + urlDetailUsersDTO);
        JsonNode accounts = restTemplate
                .exchange(urlHostSpringJPA8081 + urlDetailUsersDTO
                        , HttpMethod.POST
                        , new HttpEntity<>(httpHeaders)
                        , JsonNode.class).getBody();
        ObjectMapper mapper = new ObjectMapper();
        List<UsersDTO> usersDTOS = mapper.convertValue(accounts, new TypeReference<>() {
        });
        if (usersDTOS != null) {
            usersDTOS.stream().filter(Objects::nonNull).forEach(usersDTO -> {
                        System.out.println("usersDTO.getEmail() = " + usersDTO.getEmail());
                        usersService.addUsers(usersDTO.getId()
                                , usersDTO.getEmail()
                                , usersDTO.getUsername()
                                , usersDTO.getPassword()
                                , usersDTO.isVerify_active());
                    }
            );
        }
    }
}
