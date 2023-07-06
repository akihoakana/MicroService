package com.example.UserSpringJPA8081.controller;

import com.example.UserSpringJPA8081.facade.TokenReadFacade;
import com.example.UserSpringJPA8081.facade.TokenWriteFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {
    private static Logger logger = LogManager.getLogger(TokenController.class);

    @Autowired
    private TokenReadFacade tokenReadFacade;
    @Autowired
    private TokenWriteFacade tokenWriteFacade;

    @PostMapping("/getTokens")
    public ResponseEntity<?> getTokens() {
        return ResponseEntity.ok(tokenReadFacade.getTokens());
    }

    @PostMapping("/tokenFindByIdUsers/{id}")
    public ResponseEntity<?> tokenFindByIdUsers(@PathVariable int id) {
        return ResponseEntity.ok(tokenReadFacade.tokenFindByIdUsers(id));
    }

    @PostMapping("/deleteTokensById")
    public ResponseEntity<?> deleteTokensById(@RequestParam(name = "userId") int userId) {
        tokenWriteFacade.deleteTokensById(userId);
        return ResponseEntity.ok("Da delete roles");
    }

    @PostMapping("/updateTokensById")
    public ResponseEntity<?> updateTokensById(@RequestParam(name = "userId") int userId
            , @RequestParam(name = "token") String token
            , @RequestParam(name = "tokenRefresh") String tokenRefresh) {
        tokenWriteFacade.updateTokensById(userId, token, tokenRefresh);
        return ResponseEntity.ok("Da update roles");
    }

    @GetMapping("/user/hello1")
    public ResponseEntity<?> helloWord1() {
        return ResponseEntity.ok("helloWord I'm Demo");
    }

}
