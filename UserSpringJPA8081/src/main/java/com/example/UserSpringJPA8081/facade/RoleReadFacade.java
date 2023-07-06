package com.example.UserSpringJPA8081.facade;

import com.example.UserSpringJPA8081.entity.RolesEntity;
import com.example.UserSpringJPA8081.repository.RolesRepository;
import com.example.UserSpringJPA8081.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleReadFacade {
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private RolesService rolesService;


    public List<RolesEntity> getRoles() {
        return rolesRepository.findAll();
    }

    public Optional<RolesEntity> rolesFindById(int id) {
        return rolesRepository.findById(id);
    }

    public RolesEntity addRoles(String nameRole, String descriptionRole) {
        return rolesService.addRoles(nameRole, descriptionRole);
    }

}
