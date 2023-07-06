package com.example.UserSpringJPA8081.facade;

import com.example.UserSpringJPA8081.entity.NotificationEntity;
import com.example.UserSpringJPA8081.repository.NotificationRepository;
import com.example.UserSpringJPA8081.repository.UsersRepository;
import com.example.UserSpringJPA8081.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class NotificationFacade {


    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private NotificationService notificationService;

    public List<NotificationEntity> getNotification() {
        return notificationRepository.findAll();
    }

    public Optional<NotificationEntity> notificationFindById(int id) {
        return notificationRepository.findById(id);
    }

    public List<NotificationEntity> findByUsersEntity(int id) {
        return notificationRepository.findByUsersEntity(usersRepository.findById(id));
    }

    public NotificationEntity addNotification(int userId, String detail) {
        return notificationService.addNotification(userId, detail);
    }

    public void deleteNotificationById(int id) {
        notificationRepository.deleteById(id);
    }

    public void updateNotificationById(int id, String detail) {
        notificationRepository.updateNotificationById(id, detail);
    }
}
