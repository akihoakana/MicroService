package com.example.SpringBatch.service;

import com.example.SpringBatch.entity.UsersEntity;
import com.example.SpringBatch.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public int updateFullNameByUserId(String fullName,int userId){
       return usersRepository.updateFullNameByUserId(fullName,userId);
    }
}
