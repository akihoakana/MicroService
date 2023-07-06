package com.example.UserSpringJPA8081.service;

import com.example.UserSpringJPA8081.repository.TokensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokensService {

    @Autowired
    private TokensRepository tokensRepository;

    public void deleteTokensById(int idUsers) {
        tokensRepository.deleteByIdUsers(idUsers);
    }

    public void updateTokensById(int idUsers, String token, String tokenRefresh) {
        tokensRepository.updateTokenByIdUsers(idUsers, token, tokenRefresh);
    }
}
