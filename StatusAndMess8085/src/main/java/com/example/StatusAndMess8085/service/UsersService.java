package com.example.StatusAndMess8085.service;

import com.example.StatusAndMess8085.entity.UsersEntity;
import com.example.StatusAndMess8085.repository.MessRepository;
import com.example.StatusAndMess8085.repository.ReactionStatusRepository;
import com.example.StatusAndMess8085.repository.StatusRepository;
import com.example.StatusAndMess8085.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private MessRepository messRepository;
    @Autowired
    private ReactionStatusRepository reactionStatusRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addUsers(int id, String email, String username
            , String password, boolean verify_active) {
        entityManager.createNativeQuery("INSERT INTO users(id,email,username,password,verify_active) " +
                "VALUES (?,?,?,?,?) ")
                .setParameter(1, id)
                .setParameter(2, email)
                .setParameter(3, username)
                .setParameter(4, password)
                .setParameter(5, verify_active)
                .executeUpdate();
    }

    public UsersEntity addNextIdUsers(int id, String email, String username
            , String password, boolean verify_active) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(id);
        usersEntity.setEmail(email);
        usersEntity.setUsername(username);
        usersEntity.setPassword(password);
        usersEntity.setVerify_active(verify_active);
        return usersRepository.save(usersEntity);
    }

    public void deleteAllUser() {
        messRepository.findAll().forEach(messEntity -> {
            messRepository.deleteById(messEntity.getId());
        });
        reactionStatusRepository.findAll().forEach(reactionStatusEntity -> {
            reactionStatusRepository.deleteById(reactionStatusEntity.getId());
        });
        statusRepository.findAll().forEach(statusEntity -> {
            statusRepository.deleteById(statusEntity.getId());
        });
        usersRepository.findAll().forEach(usersEntity -> {
            usersRepository.deleteById(usersEntity.getId());
        });

    }

}
