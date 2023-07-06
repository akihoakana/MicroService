package com.example.UserSpringJPA8081.config;

import com.example.UserSpringJPA8081.entity.RolesEntity;
import com.example.UserSpringJPA8081.entity.UsersEntity;
import com.example.UserSpringJPA8081.service.UsersRolesService;
import com.example.UserSpringJPA8081.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthentProvider implements AuthenticationProvider {

    @Autowired
    private UsersService usersService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UsersRolesService usersRolesService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UsersEntity userEntity = usersService.getUsers(email);
        if (userEntity != null) {
            boolean isMatchPassword = passwordEncoder.matches(password, userEntity.getPassword());
            if (isMatchPassword && userEntity.isVerify_active()) {
                List<GrantedAuthority> authList = usersRolesService.getRolesByUserId(userEntity.getId())
                        .stream()
                        .map(RolesEntity::getName)
                        .map(SimpleGrantedAuthority::new)
                        .peek(role -> System.out.println("role.getAuthority() = " + role.getAuthority()))
                        .collect(Collectors.toList());
                return new UsernamePasswordAuthenticationToken(
                        userEntity.getEmail(), userEntity.getPassword(), authList);
            } else {
                System.out.println("isMatchPassword = " + isMatchPassword);
                System.out.println("userEntity.isVerify_active() = " + userEntity.isVerify_active());
                return null;
            }
        } else {
            System.out.println("userEntity = " + userEntity);
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}