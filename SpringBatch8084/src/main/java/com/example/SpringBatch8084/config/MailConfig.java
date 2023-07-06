package com.example.SpringBatch8084.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${myMail.testFrom.username}")
    private String username;
    @Value("${myMail.testFrom.password}")
    private String password;

    @Bean
    public JavaMailSenderImpl mailSender() {
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(username);
        mailSender.setPassword(password);
        //Password: Demotest
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
