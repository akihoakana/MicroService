package com.example.UserSpringJPA8081.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHeader(request);
        System.out.println("token doFilterInternal = " + token);
        if (token != null) {
            if (jwtService.isTokenValid(token)) {
                System.out.println("jwtService.isTokenValid(token) = " + jwtService.isTokenValid(token));
                String json = jwtService.extractClaim(token, Claims::getSubject);
                System.out.println("json = " + jwtService.extractClaim(token, Claims::getExpiration));
                System.out.println("time doFilterInternal+ " +String.format("Current Date/Time : %tc  + %tN", new Date(), new Date()));
                Map map = gson.fromJson(json, Map.class);
                System.out.println("map.get(\"roles\").toString() = " + map.get("roles").toString());
                List<GrantedAuthority> roles =
                        Arrays.stream(map.get("roles").toString()
                                .replace("[", "")
                                .replace("]", "")
                                .split(", "))
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
                roles.forEach(grantedAuthority -> System.out.println("grantedAuthority.getAuthority() = " + grantedAuthority.getAuthority()));
                if (StringUtils.hasText(map.get("type").toString())
                        && !map.get("type").toString().equals("refesh")) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    map.get("email"), ""
                                    , roles);
                    System.out.println("authenticationToken.toString() = " + authenticationToken.toString());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(authenticationToken);
                }
            } else System.out.println("JwtTokenFilter token invalid");
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String strToken = request.getHeader("Authorization");
        if (StringUtils.hasText(strToken) && strToken.startsWith("Bearer ")) {
            String finalToken = strToken.substring(7);
            return finalToken;
        } else {
            return null;
        }
    }

}
