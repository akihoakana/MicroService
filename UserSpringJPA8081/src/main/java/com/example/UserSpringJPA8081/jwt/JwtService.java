package com.example.UserSpringJPA8081.jwt;

import com.example.UserSpringJPA8081.entity.UsersEntity;
import com.example.UserSpringJPA8081.service.UsersService;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    @Autowired
    UsersService usersService;
    private Gson gson = new Gson();

    public boolean isTokenValid(String token) {
        System.out.println("token isTokenValid= " + token);
        System.out.println("time isTokenValid+ " +String.format("Current Date/Time : %tc  + %tN", new Date(), new Date()));

        String json = extractClaim(token, Claims::getSubject);
        Map map = gson.fromJson(json, Map.class);
        UsersEntity usersEntity = usersService.getUsers((String) map.get("email"));
        if (usersEntity != null) {
            if (usersEntity.getTokensEntities().getToken().equals(token) || usersEntity.getTokensEntities().getTokenRefresh().equals(token)) {
                if (extractClaim(token, Claims::getExpiration).after(new Date()) && !usersEntity.getTokensEntities().isRevoked() && !usersEntity.getTokensEntities().isExpired()) {
                    System.out.println("good");
                    return true;
                } else {
                    System.out.println("good1");
                    return false;
                }
            } else {
                System.out.println("good2");
                return false;
            }
        } else {
            System.out.println("good3");
            return false;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(
                Jwts
                        .parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
                        .build()
                        .parseClaimsJws(token)
                        .getBody());
    }


    public String generateToken(String data, String type
            , Collection<? extends GrantedAuthority> grantedAuthorities
            , long expiredDate) {
        System.out.println("grantedAuthorities = " + grantedAuthorities.size());
        System.out.println("grantedAuthorities = " + grantedAuthorities.toString());
        grantedAuthorities.forEach(grantedAuthorities1 -> grantedAuthorities1.getAuthority());
        Map<String, Object> subJectData = new HashMap<>();
        subJectData.put("email", data);
        subJectData.put("type", type);
        subJectData.put("roles", grantedAuthorities.toString());
        String json = gson.toJson(subJectData);
        System.out.println("new Date(System.currentTimeMillis() + expiredDate = " + new Date(System.currentTimeMillis() + expiredDate));
        return Jwts
                .builder()
                .setSubject(json)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredDate))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)), SignatureAlgorithm.HS256)
                .compact();
    }
}
