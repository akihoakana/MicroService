package com.example.StatusAndMess8085.facade;

import com.example.StatusAndMess8085.repository.*;
import com.example.StatusAndMess8085.service.ReactionService;
import com.example.StatusAndMess8085.service.ReactionStatusService;
import com.example.StatusAndMess8085.service.UsersService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ReactionWriterFacade {

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
    private ReactionService reactionService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void updateReaction(int id, String detail) {
        reactionRepository.updateReaction(id, detail);
    }

    public void deleteReaction(int id) {
        reactionRepository.deleteById(id);
    }
}
