package com.fome.projectfome.dto;

import org.springframework.stereotype.Repository;

import com.fome.projectfome.dto.enums.EUserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Repository
public class AuthenticationUserDTO {
    private String username;
    private String password;
    private EUserRole role;
}


