package com.example.StatusAndMess8085.facade;

import com.example.StatusAndMess8085.DTO.ReactionNotificationDTO;
import com.example.StatusAndMess8085.DTO.RestDTO;
import com.example.StatusAndMess8085.DTO.StatusDTO;
import com.example.StatusAndMess8085.DTO.StatusNotificationDTO;
import com.example.StatusAndMess8085.config.Rabbit;
import com.example.StatusAndMess8085.entity.MessEntity;
import com.example.StatusAndMess8085.entity.ReactionStatusEntity;
import com.example.StatusAndMess8085.entity.StatusEntity;
import com.example.StatusAndMess8085.feignclient.StatusAndMessFeignClient;
import com.example.StatusAndMess8085.repository.ReactionStatusRepository;
import com.example.StatusAndMess8085.repository.StatusRepository;
import com.example.StatusAndMess8085.service.ReactionStatusService;
import com.example.StatusAndMess8085.service.StatusService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class StatusReadFacade {
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private StatusService statusService;
    @Autowired
    private ReactionStatusRepository reactionStatusRepository;
    @Autowired
    private ReactionStatusService reactionStatusService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StatusAndMessFeignClient statusAndMessFeignClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Rabbit rabbit;

    @Value("${myHostService.springJPA8081}")
    private String urlHostSpringJPA8081;
    @Value("${myHostService.statusAndMess8085}")
    private String urlHostStatusAndMess8085;
    @Value("${myHostService.uploadFile8082}")
    private String urlHostUploadFile8082;
    @Value("${myDetailService.springJPA8081.RestDTO}")
    private String urlDetailRestDTO;
    @Value("${myDetailService.mess.findById3}")
    private String urlDetailFindById3;
    @Value("${myDetailService.mess.deleteMess4}")
    private String urlDetailDeleteMess4;
    @Value("${myDetailService.image.uploadImageTest}")
    private String urlDetailUploadImageTest;

    @Value("${myRabbit.exchange}")
    private String exchange;
    @Value("${myRabbit.StatusQueue.routingKey}")
    private String routingKeyStatusQueue;
    @Value("${myRabbit.ReactionQueue.routingKey}")
    private String routingKeyReactionQueue;

    public List<StatusEntity> getStatus() {
        return statusRepository.findAll();
    }

    public List<ReactionStatusEntity> getReactionStatus() {
        return reactionStatusRepository.findAll();
    }

    public StatusDTO statusDTOById(int id) {
        return reactionStatusService.countLikeByPostId(id);
    }

    public RestDTO RestDTO(HttpHeaders httpHeaders) {
        System.out.println("urlDetailRestDTO = " + urlHostSpringJPA8081 + urlDetailRestDTO);
        return restTemplate.exchange(
                urlHostSpringJPA8081 + urlDetailRestDTO
                , HttpMethod.POST
                , new HttpEntity<>(httpHeaders)
                , RestDTO.class).getBody();
    }

    public MessEntity callApi(HttpHeaders httpHeaders) {
        System.out.println("urlDetailFindById3 = " + urlHostStatusAndMess8085 + urlDetailFindById3);
        return restTemplate.exchange(
                urlHostStatusAndMess8085 + urlDetailFindById3
                , HttpMethod.POST
                , new HttpEntity<>(httpHeaders)
                , MessEntity.class).getBody();
    }

    public List<ReactionStatusEntity> getReactionStatusById(int id) {
        return reactionStatusRepository.findByStatusEntityId(id);
    }

    public Optional<MessEntity> messFindById(int id) {
        return statusAndMessFeignClient.findMessById(id);
    }

    public Optional<StatusEntity> findStatusById(int id) {
        return statusRepository.findById(id);
    }

    public Optional<ReactionStatusEntity> findReactionStatusById(int id) {
        return reactionStatusRepository.findById(id);
    }

    public StatusEntity addStatus(String detail, int idUser) {
        StatusEntity statusEntity = statusService.addStatus(detail, idUser);
        if (statusEntity!=null){
            StatusNotificationDTO statusNotificationDTO
                    = new StatusNotificationDTO(statusEntity.getId(), idUser
                    , statusEntity.getDetail(), statusEntity.getCreated());
            System.out.println(exchange + "  " + routingKeyStatusQueue);
            rabbit.outputStatusQueue().send(
                    MessageBuilder.withPayload(statusNotificationDTO).build());
//        rabbitTemplate.convertAndSend(
//                exchange,
//                routingKeyStatusQueue,
//                statusNotificationDTO);
            return statusEntity;
        }
        else return null;

    }

    public ReactionStatusEntity addReactionStatus(int userId, int reactionId, int statusId) {
        ReactionStatusEntity reactionStatusEntity = reactionStatusService.addReactionStatus(userId, reactionId, statusId);
        if (reactionStatusEntity != null) {
            ReactionNotificationDTO reactionNotificationDTO
                = new ReactionNotificationDTO(reactionStatusEntity.getId()
                , reactionStatusEntity.getStatusEntity().getUsersEntity().getId()
                , userId
                , reactionStatusEntity.getStatusEntity().getId()
                , reactionStatusEntity.getReactionEntity().getDetail()
                , reactionStatusEntity.getCreated());
            System.out.println(exchange + "  " + routingKeyReactionQueue);
            rabbit.outputReactionQueue().send(
                    MessageBuilder.withPayload(reactionNotificationDTO).build());
//            rabbitTemplate.convertAndSend(
//                    exchange,
//                    routingKeyReactionQueue,
//                    reactionNotificationDTO);
            return reactionStatusEntity;
        }
        else return null;
    }

    public List<StatusEntity> findStatusByDetail(String detail) {
        return statusRepository.findByDetail(detail);
    }
}
