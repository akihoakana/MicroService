package com.example.UserSpringJPA8081.facade;

import com.example.UserSpringJPA8081.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleWriteFacade {
    @Autowired
    private RolesService rolesService;

    public void deleteRolesById(int roleId) {
        rolesService.deleteRolesById(roleId);
    }

    public void updateRolesById(int roleId, String nameRole) {
        rolesService.updateRolesById(roleId, nameRole);
    }

}
