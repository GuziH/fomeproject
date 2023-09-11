package com.fome.projectfome.service;

import java.util.ArrayList;

import com.fome.projectfome.dao.UserRepository;
import com.fome.projectfome.dto.AuthenticationUserDTO;
import com.fome.projectfome.dto.Exception.HandlerException;
import com.fome.projectfome.model.User;
import com.fome.projectfome.utils.HashManagerUtils;
import com.fome.projectfome.utils.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtHandler;

    public Iterable<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String execute(AuthenticationUserDTO user) {
        
        try{

            Iterable<User> result = findByUsername(user.getUsername());

            List<User> idFound = getUsers(result);

            User userFound = idFound.get(0);

            if (userFound.getUsername().equals(user.getUsername()) 
                && Boolean.TRUE.equals(HashManagerUtils.validateHash(user.getPassword(), userFound.getPassword()))
            ) {
                return jwtHandler.generateToken(userFound.getUsername(), userFound.getUserRole());

            }else{
                throw new HandlerException("Invalid credentials");
            }
        } catch (Exception e) {
            throw new HandlerException("Invalid credentials");
        }
    }

    public List<User> getUsers(Iterable<User> users) {
        List<User> list = new ArrayList<>();
        for (User user : users) {
            list.add(user);
        }
        return list;
    }
}

