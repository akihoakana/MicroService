package com.example.UserSpringJPA8081.service;

import com.example.UserSpringJPA8081.entity.NotificationEntity;
import com.example.UserSpringJPA8081.repository.NotificationRepository;
import com.example.UserSpringJPA8081.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UsersRepository usersRepository;

    public NotificationEntity addNotification(int userId, String detail) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setUsersEntity(usersRepository.findById(userId).get());
        notificationEntity.setDetail(detail);
        notificationEntity.setCreated(new Date(System.currentTimeMillis()));
        return notificationRepository.save(notificationEntity);
    }
}
