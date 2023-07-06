package com.example.StatusAndMess8085.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBinding(Rabbit.class)
public class RabbitMQReceive {

    @StreamListener(Rabbit.inputGateWayQueue)
    public void receiveMessage(String mess) {
        System.out.println("mess = " + mess);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

}
