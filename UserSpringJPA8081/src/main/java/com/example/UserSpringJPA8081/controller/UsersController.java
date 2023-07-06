package com.example.UserSpringJPA8081.controller;

import com.example.UserSpringJPA8081.DTO.RestDTO;
import com.example.UserSpringJPA8081.entity.UsersEntity;
import com.example.UserSpringJPA8081.facade.UserReadFacade;
import com.example.UserSpringJPA8081.facade.UserWriteFacade;
import com.example.UserSpringJPA8081.payload.request.SignInRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/userController")
public class UsersController {

    @Autowired
    private UserReadFacade userReadFacade;
    @Autowired
    private UserWriteFacade userWriteFacade;

    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER_DEFAULT')")
    @PostMapping("/customer")
    public String customer() {
        System.out.println("Hello I'm customer");
        return SecurityContextHolder.getContext().toString();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/admin")
    public String admin() {
        System.out.println("Hello I'm admin");
        return SecurityContextHolder.getContext().toString();
    }

    @RequestMapping("/hello")
    public ResponseEntity<?> helloWord() {
        System.out.println("helloWord I'm Demo");
        return ResponseEntity.ok("helloWord I'm Demo");
    }


    @GetMapping("/user/hello1")
    public ResponseEntity<?> helloWord1() {
        return ResponseEntity.ok("helloWord I'm Demo");
    }

    @PostMapping("/RestDTO")
    public ResponseEntity<?> RestDTO() {
        return ResponseEntity.ok(new RestDTO(1, "aaaa", "12131231"));
    }


    @PostMapping("/getDataWithUserEmailDTO")
    public ResponseEntity<?> getDataWithUserEmailDTO() {
        return ResponseEntity.ok(userReadFacade.getDataWithUserEmailDTO());
    }

    @PostMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userReadFacade.getUsers());
    }

    @PostMapping("/findByEmail")
    public ResponseEntity<?> findByEmail() {
        return ResponseEntity.ok(userReadFacade.findByEmail("akihoakana@gmail.com"));
    }

    @PostMapping("/usersDTO")
    public ResponseEntity<?> usersDTO() {
        return ResponseEntity.ok(userReadFacade.usersDTO());
    }

    @PostMapping("/findByEmailId/{id}")
    public ResponseEntity<?> findEmailById(@PathVariable int id) {
        return ResponseEntity.ok(userReadFacade.findEmailById(id));
    }

    @PostMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return ResponseEntity.ok(userReadFacade.findById(id));
    }

    @PostMapping("/deleteUsersById/{id}")
    public ResponseEntity<?> deleteUsersById(@PathVariable int id) {
        userWriteFacade.deleteUsersById(id);
        return ResponseEntity.ok("Đã delete user " + id);
    }

    @PostMapping("/updateUsers")
    public ResponseEntity<?> updateUsers(@RequestParam("id") int id
            , @RequestParam("email") String email
            , @RequestParam("username") String username
            , @RequestParam("password") String password
            , @RequestParam("verify_active") boolean verify_active) {
        userWriteFacade.updateUsers(id, email, username, password, verify_active);
        return ResponseEntity.ok("Đã update user " + id);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUserByUser(@RequestBody UsersEntity usersEntity) {
        return ResponseEntity.ok(userReadFacade.updateUserByUser(usersEntity));
    }

    @PostMapping("/addListUser")
    public ResponseEntity<?> addListUser(@Valid @RequestBody List<SignInRequest> signInRequest, HttpServletRequest request) {
        return ResponseEntity.ok(userReadFacade.addListUser(signInRequest, request));
    }

    @PostMapping("/addUserGetString")
    public ResponseEntity<?> addUserGetString(@Valid @RequestBody SignInRequest signInRequest, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        userWriteFacade.addUserGetString(signInRequest, request);
        return ResponseEntity.ok(signInRequest.getEmail());
    }

    @PostMapping("/namedQuery")
    public ResponseEntity<?> selectUser() {
        return ResponseEntity.ok(userReadFacade.selectUser());
    }

}
