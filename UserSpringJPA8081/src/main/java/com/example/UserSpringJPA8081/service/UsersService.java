package com.example.UserSpringJPA8081.service;

import com.example.UserSpringJPA8081.entity.UsersEntity;
import com.example.UserSpringJPA8081.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public UsersEntity getUsers(String email) {
        return usersRepository.findByEmail(email).get();
    }

    public void deleteUserById(int userId) {
        usersRepository.deleteById(userId);
    }

    public UsersEntity updateUserByUser(UsersEntity usersEntity) {
        UsersEntity usersEntity1 = usersRepository.findByEmail(usersEntity.getEmail()).get();
        usersEntity1.setAvatar(usersEntity.getAvatar());
        usersEntity1.setFirstname(usersEntity.getFirstname());
        usersEntity1.setLastname(usersEntity.getLastname());
        usersEntity1.setPhone(usersEntity.getPhone());
        usersEntity1.setUsername(usersEntity.getUsername());
        return usersRepository.save(usersEntity1);
    }

    public List<UsersEntity> addListUser(List<UsersEntity> list) {
        return usersRepository.saveAll(list);
    }

}
