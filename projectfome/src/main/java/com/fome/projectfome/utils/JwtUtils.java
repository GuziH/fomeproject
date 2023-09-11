package com.fome.projectfome.utils;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import com.fome.projectfome.dto.Exception.HandlerException;
import com.fome.projectfome.dto.enums.EUserRole;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtils {
    public static final long JWT_TOKEN_TIME = 5*60*60;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final String secret = Base64.getEncoder().encodeToString("segredo".getBytes());

    public String generateToken(String id, EUserRole role) {
        
        return Jwts.builder()
        .setSubject(role.toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_TIME*1000))
        .signWith(SignatureAlgorithm.HS512, secret)
        .setId(id)
        .compact();
    }

    public String getRoleFromToken(String token) {
        final Date expiration = isTokenExpired(token);
        if(!expiration.before(new Date())){
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        } else {
            throw new HandlerException("login expired");
        }
    }

    public String getIdFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .getId();
    }



    private Date isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
    }

    public Boolean validateToken(String authToken, EUserRole role) {
        final Date expiration = isTokenExpired(authToken);
        return role.toString().equals(getRoleFromToken(authToken)) && !expiration.before(new Date());
    }
}