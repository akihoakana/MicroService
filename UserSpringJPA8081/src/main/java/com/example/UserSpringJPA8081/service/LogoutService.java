package com.example.UserSpringJPA8081.service;

import com.example.UserSpringJPA8081.entity.UsersEntity;
import com.example.UserSpringJPA8081.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        UsersEntity usersEntity = usersService.getUsers(authentication.getName());
        jwt = authHeader.substring(7);
        if (usersEntity != null) {
            if (usersEntity.getTokensEntities().getToken().equals(jwt)) {
                usersEntity.getTokensEntities().setExpired(true);
                usersEntity.getTokensEntities().setRevoked(true);
                usersEntity.getTokensEntities().setToken("");
                usersEntity.getTokensEntities().setTokenRefresh("");
                usersRepository.save(usersEntity);
                SecurityContextHolder.clearContext();
            } else System.out.println("Khong trung token voi token cua user co email authentication.getName() ");

        } else System.out.println("Khong tim thay user co email authentication.getName()");

    }
}
