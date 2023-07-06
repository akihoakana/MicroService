package com.example.UserSpringJPA8081.controller;

import com.example.UserSpringJPA8081.facade.RoleReadFacade;
import com.example.UserSpringJPA8081.facade.RoleWriteFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    private static Logger logger = LogManager.getLogger(RoleController.class);

    @Autowired
    private RoleReadFacade roleReadFacade;
    @Autowired
    private RoleWriteFacade roleWriteFacade;


    @PostMapping("/getRoles")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok(roleReadFacade.getRoles());
    }

    @PostMapping("/findById/{id}")
    public ResponseEntity<?> rolesFindById(@PathVariable int id) {
        return ResponseEntity.ok(roleReadFacade.rolesFindById(id));
    }

    @PostMapping("/addRoles")
    public ResponseEntity<?> addRoles(@RequestParam(name = "nameRole") String nameRole
            , @RequestParam(name = "descriptionRole") String descriptionRole) {
        return ResponseEntity.ok(roleReadFacade.addRoles(nameRole, descriptionRole));
    }

    @PostMapping("/deleteRoles")
    public ResponseEntity<?> deleteRolesById(@RequestParam(name = "roleId") int roleId) {
        roleWriteFacade.deleteRolesById(roleId);
        return ResponseEntity.ok("Da delete roles");
    }

    @PostMapping("/updateRoles")
    public ResponseEntity<?> updateRolesById(@RequestParam(name = "roleId") int roleId
            , @RequestParam(name = "nameRole") String nameRole) {
        roleWriteFacade.updateRolesById(roleId, nameRole);
        return ResponseEntity.ok("Da update roles");
    }


    @RequestMapping("/hello")
    public ResponseEntity<?> helloWord() {
        System.out.println("helloWord I'm Demo");
        return ResponseEntity.ok("helloWord I'm Demo");
    }

}
