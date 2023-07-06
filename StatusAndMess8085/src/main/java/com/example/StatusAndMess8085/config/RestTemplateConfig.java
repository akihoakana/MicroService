package com.example.StatusAndMess8085.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    //    @LoadBalanced // Load balance between service instances running at different ports.
    @Bean
    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
//        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
//        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
//        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
//        restTemplate.getMessageConverters().add(new ResourceHttpMessageConverter());
//        restTemplate.getMessageConverters().add(new SourceHttpMessageConverter<>());
//        restTemplate.getMessageConverters().add(new AllEncompassingFormHttpMessageConverter());
//        return restTemplate;
        return new RestTemplate();
    }
}
