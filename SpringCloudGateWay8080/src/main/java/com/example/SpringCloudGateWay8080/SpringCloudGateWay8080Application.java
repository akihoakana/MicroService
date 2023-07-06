package com.example.SpringCloudGateWay8080;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
@EnableSwagger2
public class SpringCloudGateWay8080Application {


    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGateWay8080Application.class, args);
    }

}
