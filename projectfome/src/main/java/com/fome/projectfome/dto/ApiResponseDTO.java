package com.fome.projectfome.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDTO {
    private boolean success;
    private Object message;

    public ApiResponseDTO(boolean success, Object message){
        this.success = success;
        this.message = message;
    }

    public ApiResponseDTO(Object message){
        this.success = true;
        this.message = message;
    }
}
