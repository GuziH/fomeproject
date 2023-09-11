package com.fome.projectfome.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fome.projectfome.dto.enums.EUserRole;
import com.fome.projectfome.dto.Exception.HandlerException;
import com.fome.projectfome.utils.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ValidationTokenService {

    @Autowired
    private JwtUtils jwtHandler;

    public String getRoleFromToken(HttpServletRequest request) {
        String token;
        try {
            token = getToken(request);

            return jwtHandler.getRoleFromToken(token);
            
        } catch (ExpiredJwtException e) {
            //Exemplo de token expirado: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJIUl9ST0xFIiwiaWF0IjoxNjYzMzcyOTE2LCJleHAiOjE2NjMzOTA5MTZ9.ebdTmPush4cjyn544Xd1ARWHNAZ7VwlVEb_w6_76ZEKZgcKOaQ1CFDZWBxzJy3yzzIahMTO4YCKG5IqeB7rJPA
            throw new HandlerException("Login expired");
        } catch (Exception e) {
            throw new HandlerException(e.getMessage());
        }
    }

    public String getIdFromToken(HttpServletRequest request) {
        String token;
        try {
            token = getToken(request);

            return jwtHandler.getIdFromToken(token);
            
        } catch (ExpiredJwtException e) {
            //Exemplo de token expirado: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJIUl9ST0xFIiwiaWF0IjoxNjYzMzcyOTE2LCJleHAiOjE2NjMzOTA5MTZ9.ebdTmPush4cjyn544Xd1ARWHNAZ7VwlVEb_w6_76ZEKZgcKOaQ1CFDZWBxzJy3yzzIahMTO4YCKG5IqeB7rJPA
            throw new HandlerException("Login expired");
        } catch (Exception e) {
            throw new HandlerException(e.getMessage());
        }
    }

    public String execute(HttpServletRequest request, EUserRole role) {
        String token;
        String userRole = "num foi";
        try {
            token = getToken(request);
            
            boolean isValidated = jwtHandler.validateToken(token, role);
            if (isValidated){
                    userRole = jwtHandler.getIdFromToken(token);
            } else {
                userRole = "Permisson denied";
            }

        } catch (ExpiredJwtException e) {
            //Exemplo de token expirado: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJIUl9ST0xFIiwiaWF0IjoxNjYzMzcyOTE2LCJleHAiOjE2NjMzOTA5MTZ9.ebdTmPush4cjyn544Xd1ARWHNAZ7VwlVEb_w6_76ZEKZgcKOaQ1CFDZWBxzJy3yzzIahMTO4YCKG5IqeB7rJPA
            throw new HandlerException("Login expired");
        } catch (Exception e) {
            throw new HandlerException(e.getMessage());
        }
        
        return userRole;
    }

    public String getToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
    }
}
