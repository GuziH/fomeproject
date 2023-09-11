package com.fome.projectfome.dto.mappers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Password {
    
    private boolean success;
    private String password;

    public Password(boolean success, String password){

        this.success = success;
        this.password = password;

    }

}
