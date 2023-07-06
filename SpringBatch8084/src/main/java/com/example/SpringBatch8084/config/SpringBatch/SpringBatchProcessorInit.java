package com.example.SpringBatch8084.config.SpringBatch;

import com.example.SpringBatch8084.DTO.UserEmailDTO;
import com.example.SpringBatch8084.payload.request.SignInRequest;
import com.example.SpringBatch8084.service.process.ProcessorCSVTest;
import com.example.SpringBatch8084.service.process.ProcessorSaveDBByApi;
import com.example.SpringBatch8084.service.process.ProcessorScheduleTest;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableBatchProcessing
@EnableScheduling
public class SpringBatchProcessorInit {
    @Bean
    public ItemProcessor<UserEmailDTO, UserEmailDTO> itemProcessor() {
        return new ProcessorCSVTest();
    }

    @Bean
    public ItemProcessor<UserEmailDTO, UserEmailDTO> itemScheduleProcessor() {
        return new ProcessorScheduleTest();
    }

    @Bean
    public ItemProcessor<SignInRequest, SignInRequest> itemProcessor1() {
        return new ProcessorSaveDBByApi();
    }

}