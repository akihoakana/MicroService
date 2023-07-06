package com.example.UserSpringJPA8081.facade;

import com.example.UserSpringJPA8081.payload.request.SignInRequest;
import com.example.UserSpringJPA8081.repository.NotificationRepository;
import com.example.UserSpringJPA8081.repository.UsersRepository;
import com.example.UserSpringJPA8081.repository.UsersRolesRepository;
import com.example.UserSpringJPA8081.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Component
public class UserWriteFacade {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersRolesRepository usersRolesRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private RegisterService registerService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void deleteUsersById(int id) {
        usersRolesRepository.deleteByUsersId(id);
        notificationRepository.deleteNotificationByUserId(id);
        usersRepository.deleteById(id);
    }

    public void updateUsers(int id, String email, String username
            , String password, boolean verify_active) {
        usersRepository.updateUsers(id, email, username, passwordEncoder.encode(password), verify_active);
    }

    public void addUserGetString(SignInRequest signInRequest, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        String verifyURL = request.getRequestURL().toString()
                .replace(request.getServletPath(), "")
                .replace("8081", "8080") + "/register/verify/" + signInRequest.getEmail();
        registerService.registerNewUserAccount(signInRequest, verifyURL);
    }

}
