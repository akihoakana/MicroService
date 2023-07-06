package com.example.SpringBatch8084.service;

import com.example.SpringBatch8084.config.AOP.TrackTimeAOP;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
public class HelloService {
    @Autowired
    JavaMailSenderImpl mailSender;

    public JavaMailSenderImpl sendVerificationEmail(String email, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String toAddress = email;
        String fromAddress = mailSender.getUsername();
        String senderName = "Client-Service-UserSpringJPA8081";
        String subject = "Chỉ cần nhấp chuột để xác nhận";
        String content = "<h1>Xác minh địa chỉ email của bạn</h1>" +
                "Bạn vừa tạo tài khoản với địa chỉ email: [[email]]<br>" +
                "Nhấn nút \"Xác nhận\" để chứng thực địa chỉ email và mở khóa cho toàn bộ tài khoản.<br>" +
                "Chúng tôi cũng sẽ nhập các đặt phòng bạn đã thực hiện với địa chỉ email này.<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Xác nhận</a></h3>";
        content = content.replace("[[email]]", email);
        System.out.println("verifyURL = " + siteURL);
        content = content.replace("[[URL]]", siteURL);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
        return mailSender;
    }

    public JavaMailSenderImpl sendScheduleEmail(String email) throws UnsupportedEncodingException, MessagingException {
        String toAddress = email;
        String fromAddress = mailSender.getUsername();
        String senderName = "Client-Service-SpringBatch Schedule";
        String subject = "Schedule Spring Auto Send Mail";
        String content = "Mail Auto 2min/once";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
        return mailSender;
    }

    public String callDaoThrowException() {
        System.out.println("Service callDaoThrowException");
        throw new RuntimeException("");
    }

    @TrackTimeAOP
    public String helloTrackTimeAOP() {
        return "helloTrackTimeAOP";
    }
}
