package com.example.StatusAndMess8085.facade;

import com.example.StatusAndMess8085.DTO.MessDTO;
import com.example.StatusAndMess8085.DTO.MessNotificationDTO;
import com.example.StatusAndMess8085.config.Rabbit;
import com.example.StatusAndMess8085.entity.MessEntity;
import com.example.StatusAndMess8085.repository.MessRepository;
import com.example.StatusAndMess8085.repository.UsersRepository;
import com.example.StatusAndMess8085.service.MessService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MessReaderFacade {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private MessRepository messRepository;
    @Autowired
    private MessService messService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Rabbit rabbit;

    @Value("${myRabbit.exchange}")
    private String exchange;
    @Value("${myRabbit.MessQueue.routingKey}")
    private String routingKeyMessQueue;

    public List<MessDTO> getMessDTOByUsers(int fromUserId, int toUserId) {
        return messService.getMessDTOByUsers(fromUserId, toUserId);
    }

    public MessEntity fowardMess(int fromUserId, int messId, int fowardUser) {
        return messService.fowardMess(fromUserId, messId, fowardUser);
    }

    public Optional<MessEntity> findById(int id) {
        return messRepository.findById(id);
    }

    public List<MessEntity> findByFromUserAndToUser(int fromUserId, int toUserId) {
        return messRepository.findByFromUserAndToUser(
                usersRepository.findById(fromUserId)
                , usersRepository.findById(toUserId));
    }

    public List<MessEntity> messGetByUser(int id) {
        return messRepository.findByToUser(usersRepository.findById(id));
    }

    public List<MessEntity> messSendByUser(int id) {
        return messRepository.findByFromUser(usersRepository.findById(id));
    }

    public List<MessEntity> getRoles() {
        return messRepository.findAll();
    }

    public MessEntity addMess(int fromUserId, int toUserId, String detail) {
        MessEntity messEntity = messService.addMess(fromUserId, toUserId, detail);
        if (messEntity != null) {
            MessNotificationDTO messNotificationDTO
                = new MessNotificationDTO(messEntity.getId()
                , messEntity.getFromUser().getId()
                , messEntity.getToUser().getId()
                , messEntity.getMessDetail()
                , messEntity.getTime());
            System.out.println(exchange + "  " + routingKeyMessQueue);
            rabbit.outputMessQueue().send(
                    MessageBuilder.withPayload(messNotificationDTO).build());
//            rabbitTemplate.convertAndSend(
//                    exchange,
//                    routingKeyMessQueue,
//                    messNotificationDTO);
            return messEntity;
        }
        return null;
    }
}
