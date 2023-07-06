package com.example.UserSpringJPA8081.service;

import com.example.UserSpringJPA8081.entity.RolesEntity;
import com.example.UserSpringJPA8081.repository.UsersRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersRolesService {

    @Autowired
    private UsersRolesRepository usersRolesRepository;

    public List<RolesEntity> getRolesByUserId(int userId) {
        List<RolesEntity> rolesEntities = new ArrayList<>();
        usersRolesRepository.findByUsersId(userId).stream()
                .forEach(usersRolesEntity -> rolesEntities.add(usersRolesEntity.getRolesEntity()));
        return rolesEntities;
    }

//    public UsersRolesEntity addRolesByUserId(int userId, int roleId) {
//        return usersRolesRepository.addUserRole(userId,roleId);
//    }

    public void deleteRolesByUserId(int userId, int roleId) {
        usersRolesRepository.deleteByUsersIdAndRolesId(userId, roleId);
    }

    public void deleteByUsersId(int userId) {
        usersRolesRepository.deleteByUsersId(userId);
    }

    public void updateRolesByUserId(int userId, int oldRoleId, int newRoleId) {
        usersRolesRepository.updateRolesByUserId(userId, oldRoleId, newRoleId);
    }
}
