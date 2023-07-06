package com.example.UserSpringJPA8081.facade;

import com.example.UserSpringJPA8081.entity.UsersEntity;
import com.example.UserSpringJPA8081.jwt.JwtService;
import com.example.UserSpringJPA8081.payload.request.SignInRequest;
import com.example.UserSpringJPA8081.payload.response.DataResponse;
import com.example.UserSpringJPA8081.payload.response.DataTokenResponse;
import com.example.UserSpringJPA8081.service.RegisterService;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SignInFacade {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    private long expiredDate = 2 * 60 * 60 * 60 * 1000;
    private long refreshExpiredDate = 8 * 60 * 60 * 60 * 1000;
    private Gson gson = new Gson();


    public DataResponse refreshToken(String token) {
        DataResponse dataResponse = new DataResponse();
        DataTokenResponse dataTokenResponse = new DataTokenResponse();
        dataResponse.setStatus(HttpStatus.OK.value());
        dataResponse.setSuccess(true);
        dataResponse.setDesc("");
        System.out.println("token = " + token);
        if (jwtService.isTokenValid(token)) {
            String json = jwtService.extractClaim(token, Claims::getSubject);
            Map map = gson.fromJson(json, Map.class);
            System.out.println("jwtService.extractClaim(token, Claims::getExpiration) = " + jwtService.extractClaim(token, Claims::getExpiration));
            if (StringUtils.hasText(map.get("type").toString())
                    && map.get("type").toString().equals("refesh")) {
                List<GrantedAuthority> roles;
                roles = Arrays.stream(map.get("roles").toString()
                        .replace("[", "")
                        .replace("]", "")
                        .split(", "))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                String tokenAuthen = jwtService.generateToken(map.get("email").toString(), "authen", roles, expiredDate);
                System.out.println("tokenAuthen = " + tokenAuthen);
                String refeshToken = jwtService.generateToken(map.get("email").toString(), "refesh", roles, refreshExpiredDate);
                registerService.saveTokenByEmail(map.get("email").toString(), token, refeshToken);
                dataTokenResponse.setToken(tokenAuthen);
                dataTokenResponse.setRefreshToken(refeshToken);
                dataResponse.setData(dataTokenResponse);
            } else
                dataResponse.setData("Đây không phải refreshToken");

        } else {
            dataResponse.setData("Token invalid");
        }
        return dataResponse;
    }

    public DataResponse singIn(SignInRequest signInRequest) {
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        String token = jwtService.generateToken(signInRequest.getEmail(), "authen", auth.getAuthorities(), expiredDate);
        String refeshToken = jwtService.generateToken(signInRequest.getEmail(), "refesh", auth.getAuthorities(), refreshExpiredDate);
        registerService.saveTokenByEmail(signInRequest.getEmail(), token, refeshToken);
        DataTokenResponse dataTokenResponse = new DataTokenResponse();
        dataTokenResponse.setToken(token);
        dataTokenResponse.setRefreshToken(refeshToken);
        DataResponse dataResponse = new DataResponse();
        dataResponse.setStatus(HttpStatus.OK.value());
        dataResponse.setSuccess(true);
        dataResponse.setDesc("");
        dataResponse.setData(dataTokenResponse);
        return dataResponse;
    }

    public UsersEntity newPassword(String password
            , String email) {
        return registerService.newPassword(email, password);
    }

    public void forgetPasword(String email, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        String verifyURL = request.getRequestURL().toString()
                .replace(request.getServletPath(), "") + "/signin/newpassword/" + email;
        registerService.sendVerificationEmail(email, verifyURL);
    }

    public boolean validateToken(String token) {
        return jwtService.isTokenValid(token);
    }
}
