package com.example.UserSpringJPA8081.controller;

import com.example.UserSpringJPA8081.facade.UserRoleReadFacade;
import com.example.UserSpringJPA8081.facade.UserRoleWriteFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userRole")
public class UsersRoleController {
    private static Logger logger = LogManager.getLogger(UsersRoleController.class);

    @Autowired
    private UserRoleReadFacade userRoleReadFacade;
    @Autowired
    private UserRoleWriteFacade userRoleWriteFacade;

    @RequestMapping("/hello")
    public ResponseEntity<?> helloWord() {
        System.out.println("helloWord I'm Demo");
        return ResponseEntity.ok("helloWord I'm Demo");
    }

    @PostMapping("/getUsersRoles")
    public ResponseEntity<?> getUsersRoles() {
        return ResponseEntity.ok(userRoleReadFacade.getUsersRoles());
    }

    @PostMapping("/getUsersRolesRolesByUserId/{id}")
    public ResponseEntity<?> getUsersRolesRolesByUserId(@PathVariable int id) {
        return ResponseEntity.ok(userRoleReadFacade.getRolesByUserId(id));
    }

    @PostMapping("/findUsersRolesByUsersId/{id}")
    public ResponseEntity<?> findUsersRolesByUsersId(@PathVariable int id) {
        return ResponseEntity.ok(userRoleReadFacade.findByUsersId(id));
    }


    @PostMapping("/findUsersRolesByRolesId/{id}")
    public ResponseEntity<?> findUsersRolesByRolesId(@PathVariable int id) {
        return ResponseEntity.ok(userRoleReadFacade.findByRolesId(id));
    }

//    @PostMapping("/addUserRolesByUserId")
//    public ResponseEntity<?> addUserRolesByUserId(@RequestParam(name = "userId") int userId, @RequestParam(name = "roleId") int roleId) {
//        return ResponseEntity.ok(userRoleReadFacade.addRolesByUserId(userId, roleId));
//    }


    @PostMapping("/deleteUserRolesByUserId")
    public ResponseEntity<?> deleteRolesByUserId(@RequestParam(name = "userId") int userId, @RequestParam(name = "roleId") int roleId) {
        userRoleWriteFacade.deleteRolesByUserId(userId, roleId);
        return ResponseEntity.ok("Da delete roles cho user");
    }

    @PostMapping("/deleteByUsersId")
    public ResponseEntity<?> deleteByUsersId(@RequestParam(name = "userId") int userId) {
        userRoleWriteFacade.deleteByUsersId(userId);
        return ResponseEntity.ok("Da delete roles cho user " + userId);
    }

    @PostMapping("/updateRolesForUser")
    public ResponseEntity<?> updateRolesByUserId(@RequestParam(name = "userId") int userId
            , @RequestParam(name = "roleId") int roleId
            , @RequestParam(name = "newRoleId") int newRoleId) {
        userRoleWriteFacade.updateRolesByUserId(userId, roleId, newRoleId);
        return ResponseEntity.ok("Da update roles cho user");
    }
}
