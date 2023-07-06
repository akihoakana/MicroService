package com.example.UserSpringJPA8081.facade;

import com.example.UserSpringJPA8081.entity.TokensEntity;
import com.example.UserSpringJPA8081.repository.TokensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TokenReadFacade {
    @Autowired
    private TokensRepository tokensRepository;


    public Optional<TokensEntity> tokenFindByIdUsers(int id) {
        return tokensRepository.findByIdUsers(id);
    }

    public List<TokensEntity> getTokens() {
        return tokensRepository.findAll();
    }
}
