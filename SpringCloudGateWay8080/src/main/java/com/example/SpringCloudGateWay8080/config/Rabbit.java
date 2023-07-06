package com.example.SpringCloudGateWay8080.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface Rabbit {

    @Output
    MessageChannel outputGateWayQueue();

//    @Output
//    MessageChannel outputReactionQueue();
//
//    @Output
//    MessageChannel outputStatusQueue();
//
//    @Output
//    MessageChannel outputMessQueue();

}
