package com.example.StatusAndMess8085.facade;

import com.example.StatusAndMess8085.repository.*;
import com.example.StatusAndMess8085.service.MessService;
import com.example.StatusAndMess8085.service.ReactionStatusService;
import com.example.StatusAndMess8085.service.UsersService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MessWriterFacade {

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
    private MessService messService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void updateMess(int id, String detail) {
        messRepository.updateMess(id, detail);
    }

    public void deleteMess(int id) {
        messRepository.deleteById(id);
    }
}
