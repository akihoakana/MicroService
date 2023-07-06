package com.example.UserSpringJPA8081.facade;

import com.example.UserSpringJPA8081.entity.RolesEntity;
import com.example.UserSpringJPA8081.entity.UsersRolesEntity;
import com.example.UserSpringJPA8081.repository.UsersRolesRepository;
import com.example.UserSpringJPA8081.service.UsersRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRoleReadFacade {

    @Autowired
    private UsersRolesRepository usersRolesRepository;
    @Autowired
    private UsersRolesService usersRolesService;

    public List<UsersRolesEntity> getUsersRoles() {
        return usersRolesRepository.findAll();
    }

    public List<RolesEntity> getRolesByUserId(int id) {
        return usersRolesService.getRolesByUserId(id);
    }

    public List<UsersRolesEntity> findByUsersId(int id) {
        return usersRolesRepository.findByUsersId(id);
    }

    public List<UsersRolesEntity> findByRolesId(int id) {
        return usersRolesRepository.findByRolesId(id);
    }

//    public UsersRolesEntity addRolesByUserId(int userId, int roleId) {
//        return usersRolesService.addRolesByUserId(userId, roleId);
//    }
}
