package com.fome.projectfome.dto.mappers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDTO {
    
    private String token;

    public TokenResponseDTO(String token) {
        this.token = token;
    }
}
