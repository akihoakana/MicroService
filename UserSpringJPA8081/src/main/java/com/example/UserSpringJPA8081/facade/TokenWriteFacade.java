package com.example.UserSpringJPA8081.facade;

import com.example.UserSpringJPA8081.service.TokensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenWriteFacade {
    @Autowired
    private TokensService tokensService;

    public void deleteTokensById(int userId) {
        tokensService.deleteTokensById(userId);
    }

    public void updateTokensById(int userId, String token, String tokenRefresh) {
        tokensService.updateTokensById(userId, token, tokenRefresh);
    }

}
