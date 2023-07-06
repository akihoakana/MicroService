package com.example.UserSpringJPA8081.facade;

import com.example.UserSpringJPA8081.service.UsersRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRoleWriteFacade {

    @Autowired
    private UsersRolesService usersRolesService;


    public void deleteRolesByUserId(int userId, int roleId) {
        usersRolesService.deleteRolesByUserId(userId, roleId);
    }

    public void deleteByUsersId(int userId) {
        usersRolesService.deleteByUsersId(userId);
    }

    public void updateRolesByUserId(int userId, int roleId, int newRoleId) {
        usersRolesService.updateRolesByUserId(userId, roleId, newRoleId);
    }

}
