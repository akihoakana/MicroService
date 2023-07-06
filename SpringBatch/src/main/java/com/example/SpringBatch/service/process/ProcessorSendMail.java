package com.example.SpringBatch.service.process;

import com.example.SpringBatch.entity.UsersEntity;
import com.example.SpringBatch.repository.UsersRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class ProcessorSendMail implements ItemProcessor<UsersEntity, UsersEntity> {

    @Autowired
    JavaMailSenderImpl mailSender;
    @Autowired
    UsersRepository usersRepository;
    @Override
    public UsersEntity process(UsersEntity usersEntity) throws UnsupportedEncodingException, MessagingException {
        System.out.println("ProcessorDBtoDB..." + usersEntity.getId()+ usersEntity.getEmail());
        usersEntity.setIssend(false);
        usersRepository.findAll();
        sendEmail(usersEntity.getEmail());
        return usersEntity;
    }
    private void sendEmail(String mail) throws UnsupportedEncodingException, MessagingException, UnsupportedEncodingException, MessagingException {
        String toAddress = mail;
        String fromAddress = mailSender.getUsername();
        String senderName = "Spring Batch";
        String subject = "Spring Batch sending";
        String content = "<h1>Spring Batch xin chào</h1>" +
                "Email: [[email]]<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Link Hello SpringBatch</a></h3>";
        content = content.replace("[[email]]", mail);
        content = content.replace("[[URL]]", "http://localhost:8091/api/hello");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
        System.out.println("Finished sent");
    }
    //        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//
//        mailSender.setUsername("demotest1a23456789@gmail.com");
//        mailSender.setPassword("xfoganmrtdjxesny");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
}
