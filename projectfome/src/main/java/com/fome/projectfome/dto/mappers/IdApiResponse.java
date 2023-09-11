package com.fome.projectfome.dto.mappers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdApiResponse {
    
    private boolean success;
    private int id;

    public IdApiResponse(boolean success, int id){
        this.success = success;
        this.id = id;
    }
}

