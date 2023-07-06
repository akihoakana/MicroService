package com.example.SpringCloudGateWay8080.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@EnableBinding(Rabbit.class)
public class RabbitMQController {

    @Autowired
    private Rabbit rabbit;

    @PostMapping("/send111")
    public ResponseEntity<?> sendMessage1() {
        String helloFormat = "hello routingKeyMessQueue I'm GateWay Send";
        System.out.println(rabbit.outputGateWayQueue().send(MessageBuilder.withPayload(helloFormat).build()));
        rabbit.outputGateWayQueue().send(MessageBuilder.withPayload(helloFormat).build());
        return ResponseEntity.ok("Đã gửi qua ");
    }
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//    @Value("${myRabbit.exchange}")
//    private String exchange;
//    @Value("${myRabbit.MessQueue.name}")
//    private String MessQueue;
//    @Value("${myRabbit.StatusQueue.name}")
//    private String StatusQueue;
//    @Value("${myRabbit.ReactionQueue.name}")
//    private String ReactionQueue;
//    @Value("${myRabbit.GateWayQueue.name}")
//    private String GateWayQueue;
//    @Value("${myRabbit.MessQueue.routingKey}")
//    private String routingKeyMessQueue;
//    @Value("${myRabbit.StatusQueue.routingKey}")
//    private String routingKeyStatusQueue;
//    @Value("${myRabbit.ReactionQueue.routingKey}")
//    private String routingKeyReactionQueue;
//    @Value("${myRabbit.GateWayQueue.routingKey}")
//    private String routingKeyGateWayQueue;


//    @PostMapping("/send")
//    public ResponseEntity<?> sendMessage() {
//        System.out.println("exchange = " + exchange);
//        System.out.println("routingKeyGateWayQueue = " + routingKeyGateWayQueue);
//        rabbitTemplate.convertAndSend(exchange, routingKeyGateWayQueue,
//                "hello routingKeyMessQueue I'm GateWay Send 1");
//        rabbitTemplate.convertAndSend(exchange, routingKeyGateWayQueue,
//                "hello routingKeyMessQueue I'm GateWay Send 2");
//        return ResponseEntity.ok("Đã gửi qua ");
//    }

    @RequestMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Hello");
    }

}
