package com.example.UserSpringJPA8081.config;

import com.example.UserSpringJPA8081.DTO.MessNotificationDTO;
import com.example.UserSpringJPA8081.DTO.ReactionNotificationDTO;
import com.example.UserSpringJPA8081.DTO.StatusNotificationDTO;
import com.example.UserSpringJPA8081.repository.UsersRepository;
import com.example.UserSpringJPA8081.service.NotificationService;
import com.example.UserSpringJPA8081.service.RolesService;
import com.example.UserSpringJPA8081.service.UsersService;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(RabbitSend.class)
public class RabbitMQReceive {
    @Autowired
    RolesService rolesService;
    @Autowired
    UsersService usersService;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    NotificationService notificationService;

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @StreamListener(RabbitSend.inputGateWayQueue)
    public void receiveMessage(String mess) {
        System.out.println("mess = " + mess);
    }


    @StreamListener(RabbitSend.inputReactionQueue)
    public void receiveMessageFromMessQueue(ReactionNotificationDTO reactionNotificationDTO) {
        if (reactionNotificationDTO == null) {
            System.out.println("reactionNotificationDTO = " + reactionNotificationDTO);
        } else {
            if (usersRepository.findById(reactionNotificationDTO.getIdUserReaction()) == null) {
                System.out.println("null ReactionQueue");
            } else {
                String detail =
                        usersRepository.findById(reactionNotificationDTO.getIdUserReaction()).get().getEmail()
                                + " vừa " + reactionNotificationDTO.getNameReaction()
                                + " ReactionStatusID là: " + reactionNotificationDTO.getIdReactionStatus()
                                + " và status với statusID: "
                                + reactionNotificationDTO.getIdStatus() + " của "
                                + usersRepository.findById(reactionNotificationDTO.getIdUserStatus()).get().getEmail()
                                + "  Vào lúc: "
                                + reactionNotificationDTO.getCreated();
                System.out.println("detail = " + detail);
                notificationService.addNotification(reactionNotificationDTO.getIdUserReaction(), detail);
            }
        }
    }

    @StreamListener(RabbitSend.inputStatusQueue)
    public void receiveMessageFromStatusQueue(StatusNotificationDTO statusNotificationDTO) {
        if (statusNotificationDTO == null) {
            System.out.println("statusNotificationDTO = " + statusNotificationDTO);
        } else {
            if (usersRepository.findById(statusNotificationDTO.getIdUser()) == null) {
                System.out.println("null StatusQueue");
            } else {
                String detail = usersRepository.findById(statusNotificationDTO.getIdUser()).get().getEmail()
                        + " vừa đăng 1 status với nội dung: "
                        + statusNotificationDTO.getDetail() + " - idStatus: "
                        + statusNotificationDTO.getIdStatus() + "  Vào lúc: "
                        + statusNotificationDTO.getCreated();
                System.out.println("detail = " + detail);
                notificationService.addNotification(statusNotificationDTO.getIdUser(), detail);
            }
        }
    }

    @StreamListener(RabbitSend.inputMessQueue)
    public void receiveMessageFromReactionQueue(MessNotificationDTO messNotificationDTO) {
        if (messNotificationDTO == null) {
            System.out.println("messNotificationDTO = " + messNotificationDTO);
        } else {
            if (usersRepository.findById(messNotificationDTO.getIdToUser()) == null
                    || usersRepository.findById(messNotificationDTO.getIdFromUser()) == null) {
                System.out.println("null MessQueue");
            } else {
                String detail = usersRepository.findById(messNotificationDTO.getIdToUser()).get().getEmail()
                        + " vừa nhận được 1 tin nhắn với nội dung: "
                        + messNotificationDTO.getDetail() + " - idMess: "
                        + messNotificationDTO.getIdMess()
                        + " từ : "
                        + usersRepository.findById(messNotificationDTO.getIdFromUser()).get().getEmail()
                        + "  Vào lúc: "
                        + messNotificationDTO.getCreated();
                System.out.println("detail = " + detail);
                notificationService.addNotification(messNotificationDTO.getIdToUser(), detail);
            }
        }
    }

}
