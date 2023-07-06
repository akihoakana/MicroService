package com.example.UserSpringJPA8081.service;

import com.example.UserSpringJPA8081.entity.RolesEntity;
import com.example.UserSpringJPA8081.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    public RolesEntity addRoles(String nameRole, String description) {
        RolesEntity rolesEntity = new RolesEntity();
        rolesEntity.setName(nameRole);
        rolesEntity.setDescription(description);
        return rolesRepository.save(rolesEntity);
    }

    public void deleteRolesById(int roleId) {
        rolesRepository.deleteById(roleId);
    }

    public void updateRolesById(int oldRoleId, String nameRole) {
        rolesRepository.updateRolesById(oldRoleId, nameRole);
    }
}
