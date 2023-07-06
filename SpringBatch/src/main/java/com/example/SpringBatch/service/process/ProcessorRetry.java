package com.example.SpringBatch.service.process;

import com.example.SpringBatch.entity.UsersEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class ProcessorRetry implements ItemProcessor<UsersEntity, UsersEntity> {
    @Override
    public UsersEntity process(UsersEntity usersEntity) throws UnsupportedEncodingException, MessagingException {
        System.out.println("ProcessorDBtoDB..." + usersEntity.getId()+ usersEntity.getEmail());
        UsersEntity usersEntity1 = null;
        return usersEntity1;
    }
}
