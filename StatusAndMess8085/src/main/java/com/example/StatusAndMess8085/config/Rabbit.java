package com.example.StatusAndMess8085.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface Rabbit {
    String inputGateWayQueue = "inputGateWayQueue";

    @Input
    SubscribableChannel inputGateWayQueue();

    @Output
    MessageChannel outputGateWayQueue();

    @Output
    MessageChannel outputReactionQueue();

    @Output
    MessageChannel outputStatusQueue();

    @Output
    MessageChannel outputMessQueue();
}
