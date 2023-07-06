package com.example.UserSpringJPA8081.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface RabbitSend {
    String inputGateWayQueue = "inputGateWayQueue";
    String inputReactionQueue = "inputReactionQueue";
    String inputStatusQueue = "inputStatusQueue";
    String inputMessQueue = "inputMessQueue";

    @Input
    SubscribableChannel inputGateWayQueue();

    @Input
    SubscribableChannel inputReactionQueue();

    @Input
    SubscribableChannel inputStatusQueue();

    @Input
    SubscribableChannel inputMessQueue();

}
