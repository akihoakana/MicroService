package com.example.SpringBatch8084.service.process;


import com.example.SpringBatch8084.DTO.UserEmailDTO;
import com.example.SpringBatch8084.service.HelloService;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public class ProcessorScheduleTest extends ItemListenerSupport<UserEmailDTO, UserEmailDTO> implements ItemProcessor<UserEmailDTO, UserEmailDTO> {

    @Autowired
    HelloService helloService;

    @Override
    public UserEmailDTO process(UserEmailDTO userEmailDTO) throws UnsupportedEncodingException, MessagingException {
        System.out.println("userEmailDTO.getEmail() = " + userEmailDTO.getEmail());
        helloService.sendScheduleEmail(userEmailDTO.getEmail());
        return userEmailDTO;
    }
}
