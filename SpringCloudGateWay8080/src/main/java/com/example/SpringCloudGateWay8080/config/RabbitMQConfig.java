package com.example.SpringCloudGateWay8080.config;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
//    @Bean
//    public Declarables topicBindings() {
//        Queue topicQueue1 = new Queue(topicQueue1Name, false);
//        Queue topicQueue2 = new Queue(topicQueue2Name, false);
//
//        TopicExchange topicExchange = new TopicExchange(topicExchangeName);
//
//        return new Declarables(
//                topicQueue1,
//                topicQueue2,
//                topicExchange,
//                BindingBuilder
//                        .bind(topicQueue1)
//                        .to(topicExchange).with("*.important.*"),
//                BindingBuilder
//                        .bind(topicQueue2)
//                        .to(topicExchange).with("#.error"));
//    }
}
